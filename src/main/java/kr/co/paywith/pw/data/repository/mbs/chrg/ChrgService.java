package kr.co.paywith.pw.data.repository.mbs.chrg;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.enumeration.PointRuleTypeCd;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRule;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRuleRepository;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChrgService {

  @Autowired
  ChrgRepository chrgRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private PointRuleRepository pointRuleRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public Chrg create(Chrg chrg) {

    // kms: TODO 선불카드 번호로 POS에서 온 충전이면 userInfo 언제 조회해서 넣을지 확인 필요

    UserInfo userInfo = userInfoRepository.findById(chrg.getUserInfo().getId()).get();
    chrg.setUserInfo(userInfo);

    // 데이터베이스 값 갱신
    Chrg newChrg = this.chrgRepository.save(chrg);

    if (chrg.getChrgDttm() != null) {
      this.completeChrg(newChrg);
    }

    return chrgRepository.save(newChrg);
  }

  /**
   * 정보 갱신
   */
  @Transactional
  public Chrg update(ChrgUpdateDto chrgUpdateDto, Chrg existChrg) {

    // 입력값 대입
    this.modelMapper.map(chrgUpdateDto, existChrg);

    // 데이터베이스 값 갱신
    this.chrgRepository.save(existChrg);

    if (existChrg.getChrgDttm() != null) {
      this.completeChrg(existChrg);

    }

    return chrgRepository.save(existChrg);
  }

  @Transactional
  public void delete(Chrg chrg) {
    // 취소 일시 입력
    chrg.setCancelRegDttm(ZonedDateTime.now());

    // 잔액 복원
    UserInfo userInfo = chrg.getUserInfo();
    userInfo.getUserCard().setPrpayAmt(userInfo.getUserCard().getPrpayAmt() - chrg.getChrgAmt());
    userInfo.getUserCard().setPrpayTotalAmt(userInfo.getUserCard().getPrpayTotalAmt() - chrg.getChrgAmt());
  }

  /**
   * 충전할 결제가 완료되었을 때 후속 조치.
   *
   * 일반적으로 결제를 먼저하고 POST Chrg를 하지만, 무통장 입금은 POST 시점엔 결제가 되어있지 않으므로,
   *
   * 결제 확인 후 chrg update 를 하면서 관련 처리를 한다.
   *
   * @param newChrg persist 상태의 chrg
   */
  private void completeChrg(Chrg newChrg) {
    UserInfo userInfo = newChrg.getUserInfo();
    // 운영 중 거래 식별을 위해 서버에서 생성하는 거래 번호
    newChrg.setConfmNo("D" + StringUtils.leftPad("" + newChrg.getId(), 11, "0"));

    // 선불카드 잔액 갱신
    userInfo.getUserCard().setPrpayAmt(userInfo.getUserCard().getPrpayAmt() + newChrg.getChrgAmt());
    userInfo.getUserCard().setPrpayTotalAmt(userInfo.getUserCard().getPrpayTotalAmt() + newChrg.getChrgAmt());
    userInfoRepository.save(userInfo);

    // 충전 시 포인트 적립규칙 있는지 확인
    for (PointRule pointRule :
        pointRuleRepository.findByPointRuleTypeCdAndMinAmtGreaterThanEqualAndActiveFlIsTrue(
            PointRuleTypeCd.C,
            newChrg.getChrgAmt())) {
      // 적립 규칙 있으면 적립
      // kms: TODO 멤버십 포인트 관련 정책 확인 후 구조 정해지면 개발
    }

    // TODO PgPay 관련 처리
  }

}
