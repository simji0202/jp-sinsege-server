package kr.co.paywith.pw.data.repository.mbs.delng;


import java.time.ZonedDateTime;
import javax.transaction.Transactional;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.enumeration.StampHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.delngGoods.DelngGoods;
import kr.co.paywith.pw.data.repository.mbs.delngGoodsOpt.DelngGoodsOpt;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import kr.co.paywith.pw.data.repository.mbs.gcct.Gcct;
import kr.co.paywith.pw.data.repository.mbs.gcct.GcctRepository;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRule;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRuleRepository;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHistService;
import kr.co.paywith.pw.data.repository.user.grade.GradeRepository;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userStamp.UserStamp;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DelngService {

  @Autowired
  private DelngRepository delngrRepository;

  @Autowired
  private GradeRepository gradeRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CpnRepository cpnRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private StampHistService stampHistService;

  @Autowired
  private PointRuleRepository pointRuleRepository;

  @Autowired
  private GcctRepository gcctRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public Delng create(Delng delng) {
    // 데이터베이스 값 갱신
    Delng newDelng = this.delngrRepository.save(delng);

    if (!newDelng.getUserInfo().getCertTypeCd().equals(CertTypeCd.GUEST)) {
      // 비회원이 아니면 현재 등급 저장
//      delng.setGrade(delng.getUserInfo());
    }

    int score = 0;
    int stamp = 0;

    if (newDelng.getDelngGoodsList() != null) {
      for (DelngGoods delngGoods : newDelng.getDelngGoodsList()) {
        // 스코어 가산
        score += delngGoods.getGoods().getScorePlusCnt() * delngGoods.getGoodsCnt();

        // 스탬프 가산
        stamp += delngGoods.getGoods().getStampPlusCnt() + delngGoods.getGoodsCnt();

        // 쿠폰 정보가 있다면 쿠폰 사용 처리
        Cpn cpn = delngGoods.getCpn();
        if (cpn != null) {
          cpn.setCpnSttsCd(CpnSttsCd.USED);
          cpnRepository.save(cpn);
        }

        // 상품권 정보가 있다면 상품권 사용 처리
        Gcct gcct = delngGoods.getGcct();
        if (gcct != null) {
          gcct.setUsedDttm(ZonedDateTime.now());
          gcctRepository.save(gcct);
        }

        if (delngGoods.getDelngGoodsOptList() != null) {
          for (DelngGoodsOpt delngGoodsOpt : delngGoods.getDelngGoodsOptList()) {
            // 스코어 가산
            score += delngGoodsOpt.getGoods().getScorePlusCnt() * delngGoodsOpt.getGoodsCnt();

            // 스탬프 가산
            stamp += delngGoodsOpt.getGoods().getStampPlusCnt() + delngGoodsOpt.getGoodsCnt();

            // 옵션 상품 쿠폰 정보가 있다면 쿠폰 사용 처리
            Cpn cpn2 = delngGoodsOpt.getCpn();
            if (cpn2 != null) {
              cpn2.setCpnSttsCd(CpnSttsCd.USED);
              cpnRepository.save(cpn2);
            }

            // 상품권 정보가 있다면 상품권 사용 처리
            Gcct gcct2 = delngGoodsOpt.getGcct();
            if (gcct2 != null) {
              gcct2.setUsedDttm(ZonedDateTime.now());
              gcctRepository.save(gcct2);
            }
          }
        }
      }
    }

    // 회원 정보 갱신
    UserInfo userInfo = newDelng.getUserInfo();

    // 스코어
    userInfo.setScoreCnt(userInfo.getScoreCnt() + score);

    // 스탬프
    if (stamp > 0) {
      StampHist stampHist = new StampHist();
      stampHist.setUserInfo(userInfo);
      stampHist.setStampHistTypeCd(StampHistTypeCd.RSRV);
      stampHist.setSetleDttm(ZonedDateTime.now());
      stampHist.setCnt(stamp);
      stampHist.setMrhst(delng.getMrhst());
      stampHist.setDelng(newDelng);
      stampHistService.create(stampHist);
    }

    // 변경 회원 정보 저장
    userInfoRepository.save(userInfo);

    // 거래 시 포인트 적립규칙 있는지 확인
    for (PointRule pointRule:
        pointRuleRepository.findByMinAmtGreaterThanEqualAndActiveFlIsTrue(delng.getDelngAmt())) {
      // 적립 규칙 있으면 적립
      // kms: TODO 멤버십 포인트 관련 정책 확인 후 구조 정해지면 개발
    }

    // 결제 정보 처리
    if (newDelng.getDelngPaymentList() != null) {
      for (DelngPayment delngPayment: newDelng.getDelngPaymentList()) {
        switch (delngPayment.getDelngPaymentTypeCd()) {
          case PRPAY:
            // TODO 선불카드 잔액 차감
            break;
          case CPN: // 금액 쿠폰 사용 처리
            Cpn cpn = delngPayment.getCpn();
            cpn.setCpnSttsCd(CpnSttsCd.USED);
            cpnRepository.save(cpn);
            break;
          case PG_PAY:
            // TODO PG 결제 처리
            break;
        }
      }
    }

    // 운영 중 거래 식별을 위해 서버에서 생성하는 거래 번호
    newDelng.setConfmNo("D" + StringUtils.leftPad("" + delng.getId(), 11, "0"));

    return delngrRepository.save(newDelng);
  }


//  /**
//   * 정보 갱신
//   */
//  @Transactional
//  public Delng update(DelngUpdateDto delngUpdateDto, Delng existDelng) {
//
//    // 입력값 대입
//    this.modelMapper.map(delngUpdateDto, existDelng);
//
//    // 데이터베이스 값 갱신
//    this.delngrRepository.save(existDelng);
//
//    return existDelng;
//  }

}
