package kr.co.paywith.pw.data.repository.user.user;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import kr.co.paywith.pw.component.StringUtil;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.enumeration.CertType;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssuService;
import kr.co.paywith.pw.data.repository.mbs.cpnIssuRule.CpnIssuRule;
import kr.co.paywith.pw.data.repository.mbs.cpnIssuRule.CpnIssuRuleRepository;
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

  @Autowired
  private CpnIssuRuleRepository cpnIssuRuleRepository;

  @Autowired
  private CpnIssuService cpnIssuService;

  /**
   * 일반 유저 정보 신규 등록
   */
  @Transactional
  public UserInfo save(UserInfo userInfo) {

    userInfo.setUserPw(this.passwordEncoder.encode(userInfo.getUserPw()));

    this.userInfoRepository.save(userInfo);

    // 카드 번호 생성. 중복 방지를 위해 db 저장 후 ID를 활용한다
    boolean isEndMakeNo = false;
    do {
      String noRule = "2";
      noRule += StringUtils.leftPad("" + userInfo.getId(), 8, "0"); //20000000
      noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
      String stampNo = StringUtil.makeNo(noRule);

      if (userCardRepository.findByCardNo(stampNo).isEmpty()) {
        isEndMakeNo = true;
   //     userInfo.getUserCard().setStampNo(stampNo);

        // 카드 객체 생성
        UserCard userCard = new UserCard();
        userCard.setCardNo(stampNo);

        // 카드 저장
        userCardRepository.save(userCard);

        // 유저에 카드 번호 적용
        userInfo.setUserCard(userCard);

      }
    } while (!isEndMakeNo);

    // 신규 회원 쿠폰 발급
    this.makeCpnIssuJoin(userInfo);

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

    if (!userInfoUpdateDto.getCertType().equals(userInfo.getCertType())) {
      // GUEST에서 일반 회원이 될 경우 최초 가입 쿠폰 발급. 반대의 경우는 오류 처리
      if (userInfo.getCertType().equals(CertType.GUEST)) {
        // TODO 신규 회원 쿠폰 발급
      } else if (userInfoUpdateDto.getCertType().equals(CertType.GUEST)) {
        // TODO kms: Exception 확인
        throw new RuntimeException();
      }
    }

    // 활설 - 비활성 상태 바꿀 때 outDttm 변경
    if (!userInfoUpdateDto.getActiveFl().equals(userInfo.isActiveFl())) {
      if (!userInfoUpdateDto.getActiveFl()) { // 활성 -> 비활성
        userInfo.setOutDttm(LocalDateTime.now());
      } else {
        userInfo.setOutDttm(null);
      }
    }

    if (StringUtils.isNotEmpty(userInfoUpdateDto.getUserPw())) {
      // 새 암호가 있을 때에만 암호 변경
      userInfoUpdateDto.setUserPw(this.passwordEncoder.encode(userInfoUpdateDto.getUserPw()));
    }

    if (
        userInfo.getCertType().equals(CertType.GUEST) &&
            !userInfoUpdateDto.getCertType().equals(CertType.GUEST)) {
      // GUEST 회원이 정식으로 가입한 경우
      // 신규 회원 쿠폰 지급
      this.makeCpnIssuJoin(userInfo);
    }

    // 입력값 대입
    this.modelMapper.map(userInfoUpdateDto, userInfo);
    userInfo = this.userInfoRepository.save(userInfo);

    return userInfo;
  }

  /**
   * 회원 신규 가입 쿠폰 발급
   * @param userInfo 신규 가입한 회원
   */
  private void makeCpnIssuJoin(UserInfo userInfo) {
    for (CpnIssuRule cpnIssuRule: cpnIssuRuleRepository.findByCpnIssuRuleType(CpnIssuRuleType.JOIN)) {
      CpnIssu cpnIssu = new CpnIssu();
      cpnIssu.setCpnIssuRule(cpnIssuRule);
      cpnIssu.setCpnIssuNm(cpnIssuRule.getRuleNm());
      cpnIssu.setValidEndDttm(LocalDateTime.now().plusDays(cpnIssuRule.getCpnMaster().getValidDay()));
      cpnIssu.setCreateBy(userInfo.getCreateBy());
      cpnIssu.setUpdateBy(userInfo.getCreateBy());

      Cpn cpn = new Cpn();
      cpn.setUserInfo(userInfo);
      cpnIssu.setCpnList(List.of(cpn));

      cpnIssuService.create(cpnIssu, null);

      if (StringUtils.isNotEmpty(cpnIssuRule.getMsgCn())) {
        // TODO 메시지 설정했다면 메시지 발송

      }
    }
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
    userInfo.setOutDttm(LocalDateTime.now());
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
    userDel.setCertType(userInfo.getCertType());
    userInfo.setCertType(null);
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
