package kr.co.paywith.pw.data.repository.user.userInfo;

import java.time.ZonedDateTime;
import javax.transaction.Transactional;
import kr.co.paywith.pw.component.StringUtil;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnlRepository;
import kr.co.paywith.pw.data.repository.user.grade.GradeService;
import kr.co.paywith.pw.data.repository.user.userDel.UserDel;
import kr.co.paywith.pw.data.repository.user.userDel.UserDelService;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import kr.co.paywith.pw.data.repository.user.userCard.UserCardRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

  @Value("${user-info-keep-day}")
  private Integer userInfoKeepDay = 0;

  @Autowired
  UserInfoRepository userInfoRepository;

  @Autowired
  GradeService gradeService;

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  MrhstTrmnlRepository mrhstTrmnlRepository;

  @Autowired
  UserCardRepository userCardRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private UserDelService userDelService;

  /**
   * 일반 유저 정보 신규 등록
   */
  @Transactional
  public UserInfo save(UserInfo userInfo) {

    userInfo.setUserPw(this.passwordEncoder.encode(userInfo.getUserPw()));

    this.userInfoRepository.save(userInfo);

    // 스탬프 번호 생성. 중복 방지를 위해 db 저장 후 ID를 활용한다
    boolean isEndMakeNo = false;
    do {
      String noRule = "2";
      noRule += StringUtils.leftPad("" + userInfo.getId(), 8, "0"); //20000000
      noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
      String stampNo = StringUtil.makeNo(noRule);

      if (userCardRepository.findByStampNo(stampNo).isEmpty()) {
        isEndMakeNo = true;
   //     userInfo.getUserCard().setStampNo(stampNo);

        // 스탬프 객체 생성
        UserCard userCard = new UserCard();
        userCard.setStampNo(stampNo);

        // 스탬프 번호 생성
        userCardRepository.save(userCard);

        // 유저에 스템프 번호 적요
        userInfo.setUserCard(userCard);

      }
    } while (!isEndMakeNo);


    // che2 : TODO 최초 등급 설정 (21.01.05)
    // 최초 등급 설정
   // userInfo.setGrade(gradeService.findFirstGrade());

    // TODO 신규 회원 쿠폰 발급

    return userInfoRepository.save(userInfo);
  }


  /**
   * 일반 유저 정보 갱신
   */
  @Transactional
  public UserInfo update(UserInfoUpdateDto userInfoUpdateDto, UserInfo userInfo) {

    // kms: 신규 클라이언트에서는 UserApp 직접 CRUD 예정이므로 코드 삭제
//        // 기존 List 항목 초기 설정
//        userInfo.getUserAppList().clear();

    if (!userInfoUpdateDto.getCertTypeCd().equals(userInfo.getCertTypeCd())) {
      // GUEST에서 일반 회원이 될 경우 최초 가입 쿠폰 발급. 반대의 경우는 오류 처리
      if (userInfo.getCertTypeCd().equals(CertTypeCd.GUEST)) {
        // TODO 신규 회원 쿠폰 발급
      } else if (userInfoUpdateDto.getCertTypeCd().equals(CertTypeCd.GUEST)) {
        // TODO kms: Exception 확인
        throw new RuntimeException();
      }
    }

    // 활설 - 비활성 상태 바꿀 때 outDttm 변경
    if (!userInfoUpdateDto.getActiveFl().equals(userInfo.getActiveFl())) {
      if (!userInfoUpdateDto.getActiveFl()) { // 활성 -> 비활성
        userInfo.setOutDttm(ZonedDateTime.now());
      } else {
        userInfo.setOutDttm(null);
      }
    }

    if (StringUtils.isNotEmpty(userInfoUpdateDto.getUserPw())) {
      // 새 암호가 있을 때에만 암호 변경
      userInfoUpdateDto.setUserPw(this.passwordEncoder.encode(userInfoUpdateDto.getUserPw()));
    }
    // 입력값 대입
    this.modelMapper.map(userInfoUpdateDto, userInfo);
    userInfo = this.userInfoRepository.save(userInfo);

    // kms: TODO stamp 정보 변경 맞춰서 쿠폰 발급
//    if (stampCnt > maxCnt) {
//      cpnIssuService.create()
//    }
    return userInfo;
  }

  /**
   *
   */
  @Transactional
  public UserInfo updatePw(UserInfo userInfo) {

    userInfo.setUserPw(this.passwordEncoder.encode(userInfo.getUserPw()));
    return this.userInfoRepository.save(userInfo);
  }

  /**
   * 회원의 탈퇴 처리
   */
  @Transactional
  public void outUser(UserInfo userInfo) {
    // 활설 - 비활성 상태 바꿀 때 outDttm 변경
    userInfo.setOutDttm(ZonedDateTime.now());
    userInfo.setActiveFl(false);

    if (userInfoKeepDay == 0) {
      // 탈퇴회원 정보 보유기간이 없으므로 바로 삭제
      delete(userInfo);
    }
    userInfoRepository.save(userInfo);
  }

  // TODO 스케쥴러로 주기적으로 실행하게 개발 필요
  /**
   * 탈퇴 회원 정보 삭제. 스케쥴러에 의해 설정한 회원정보 보유 기간 정책값이 지나면 개인정보 삭제 로직.
   */
  @Transactional
  public void delete(UserInfo userInfo) {
    // 삭제할 정보는 userDel로 이동
    UserDel userDel = new UserDel();
    userDel.setUserId(userInfo.getUserId());
    userInfo.setUserId(null);
    userDel.setCertKey(userInfo.getCertKey());
    userInfo.setCertKey(null);
    userDel.setCertTypeCd(userInfo.getCertTypeCd());
    userInfo.setCertTypeCd(null);
    userDel.setUserNm(userInfo.getUserNm());
    userInfo.setUserNm(null);
    userDel.setNickNm(userInfo.getNickNm());
    userInfo.setNickNm(null);
    userDel.setEmailAddr(userInfo.getEmailAddr());
    userInfo.setEmailAddr(null);
    userDel.setMobileNum(userInfo.getMobileNum());
    userInfo.setMobileNum(null);
    userDel.setUserInfo(userInfo);

    userDelService.create(userDel);
  }

}
