package kr.co.paywith.pw.data.repository.mbs.cpn;


import kr.co.paywith.pw.component.StringUtil;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleCd;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnService {

  @Autowired
  private CpnRepository cpnRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private UserInfoRepository userInfoRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public Cpn create(Cpn cpn) {
    // 데이터베이스 값 갱신
    Cpn newCpn = this.cpnRepository.save(cpn);

    // 쿠폰 번호 생성. 중복 방지를 위해 cpnIssu를 활용한다
    boolean isEndMakeNo = false;
    do {
      String noRule = "3";
      noRule += StringUtils.leftPad("" + cpn.getCpnIssu().getId(), 8, "0"); //90000000
      noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
      String cpnNo = StringUtil.makeNo(noRule);
      if (cpnRepository.findByCpnNo(cpnNo).isEmpty()) {
        isEndMakeNo = true;
        cpn.setCpnNo(cpnNo);
      }
    } while (!isEndMakeNo);
    // 쿠폰 번호는 처음 저장할 때 생성하면 사용하지 않고 만료되는 번호가 많이 남게 되므로, 최초 상세 조회시에 번호를 만든다


    return newCpn;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public Cpn update(CpnUpdateDto cpnUpdateDto, Cpn existCpn) {

    // 입력값 대입
    this.modelMapper.map(cpnUpdateDto, existCpn);

    // 데이터베이스 값 갱신
    this.cpnRepository.save(existCpn);

    return existCpn;
  }


  /**
   * 쿠폰 취소. 스탬프 쿠폰이면 스탬프 복원
   *
   * @param cpn 취소할 쿠폰
   */
  @Transactional
  public void delete(Cpn cpn) {
    cpn.setCpnSttsCd(CpnSttsCd.INVALID);

    if (cpn.getCpnIssu().getCpnIssuRule() != null &&
        cpn.getCpnIssu().getCpnIssuRule().getCpnIssuRuleCd().equals(CpnIssuRuleCd.S) // 스탬프 쿠폰이면
    ) {
      int stampMaxCnt = cpn.getCpnMaster().getBrand().getBrandSetting().getStampMaxCnt(); // 쿠폰 발급에 필요한 스탬프 개수
      // 회원의 스탬프 개수 복원
      UserCard userCard = cpn.getUserInfo().getUserCard();
      userCard.setStampCnt(userCard.getStampCnt() - stampMaxCnt);
      userCard.setStampTotalGet(userCard.getStampTotalGet() - stampMaxCnt);
      // userInfo 저장
      userInfoRepository.save(cpn.getUserInfo());
    }
    cpnRepository.save(cpn);
  }

  /**
   * 쿠폰 번호를 생성한다. 쿠폰 번호는 중복을 방지하기 위해 cpnIssu 생성 이후에 진행하며,
   * 사용하지 않고 만료되는 쿠폰에 할당되는 번호 낭비를 줄이기 위해, 회원이 쿠폰 최초 열람시에 생성
   *
   * @param cpn 번호발급이 되지 않은 쿠폰. cpnIssu가 있어야 한다. cpnNo가 없어야 하지만, 혹시 있다면 cpnNo를 그대로 리턴한다.
   * @return 새로 생성, 혹은 기존 cpn에 있던 쿠폰 번호
   */
  public String getCpnNo(Cpn cpn) {
    String cpnNo = null;
    // 쿠폰 번호 생성. 중복 방지를 위해 cpnIssu를 활용한다
    do {
      String noRule = "3";
      noRule += StringUtils.leftPad("" + cpn.getCpnIssu().getId(), 8, "0"); //90000000
      noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
      String cpnNo2 = StringUtil.makeNo(noRule);
      if (cpnRepository.findByCpnNo(cpnNo2).isEmpty()) {
        cpnNo = cpnNo2;
      }
    } while (cpnNo == null);
    return cpnNo;
  }
}
