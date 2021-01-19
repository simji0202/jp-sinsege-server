package kr.co.paywith.pw.data.repository.mbs.delng;


import java.time.ZonedDateTime;
import javax.transaction.Transactional;

import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.enumeration.StampHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.goods.GoodsRepository;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRule;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRuleRepository;
import kr.co.paywith.pw.data.repository.mbs.scoreHist.ScoreHist;
import kr.co.paywith.pw.data.repository.mbs.scoreHist.ScoreHistRepository;
import kr.co.paywith.pw.data.repository.mbs.scoreHist.ScoreHistService;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHistRepository;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHistService;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelngService {

  @Autowired
  private DelngRepository delngrRepository;

  @Autowired
  private CpnRepository cpnRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private StampHistService stampHistService;

  @Autowired
  private PointRuleRepository pointRuleRepository;

  @Autowired
  private GoodsRepository goodsRepository;

  @Autowired
  private StampHistRepository stampHistRepository;

  @Autowired
  private ScoreHistRepository scoreHistRepository;

  @Autowired
  private ScoreHistService scoreHistService;

  /**
   * 정보 등록
   */
  @Transactional
  public Delng create(Delng delng, DelngDto delngDto) {

    if (delng.getUserInfo().getId() != null) {
      delng.setUserInfo(userInfoRepository.findById(delng.getUserInfo().getId()).get());
    }

    // 데이터베이스 값 갱신
    Delng newDelng = this.delngrRepository.save(delng);

    int score = 0;
    int stamp = 0;

    if (delngDto.getDelngGoodsList() != null) {
      for (DelngGoods delngGoods : delngDto.getDelngGoodsList()) {
        // 상품 정보 조회
        Goods goods = goodsRepository.findById(delngGoods.getGoodsId()).get();

        // 스코어 가산
        score += goods.getScorePlusCnt() * delngGoods.getGoodsCnt();

        // 스탬프 가산
        stamp += goods.getStampPlusCnt() + delngGoods.getGoodsCnt();
//
//        if (delngGoods.getDelngGoodsOptList() != null) {
//          for (DelngGoodsOpt delngGoodsOpt : delngGoods.getDelngGoodsOptList()) {
//            // 스코어 가산
//            score += delngGoodsOpt.getGoods().getScorePlusCnt() * delngGoodsOpt.getGoodsCnt();
//
//            // 스탬프 가산
//            stamp += delngGoodsOpt.getGoods().getStampPlusCnt() + delngGoodsOpt.getGoodsCnt();
//
//            // 옵션 상품 쿠폰 정보가 있다면 쿠폰 사용 처리
//            Cpn cpn2 = delngGoodsOpt.getCpn();
//            if (cpn2 != null) {
//              cpn2.setCpnSttsCd(CpnSttsCd.USED);
//              cpnRepository.save(cpn2);
//            }
//
//            // 상품권 정보가 있다면 상품권 사용 처리
//            Gcct gcct2 = delngGoodsOpt.getGcct();
//            if (gcct2 != null) {
//              gcct2.setUsedDttm(ZonedDateTime.now());
//              gcctRepository.save(gcct2);
//            }
//          }
//        }
      }
    }

    // 회원 정보 갱신
    UserInfo userInfo = newDelng.getUserInfo();

    // 스코어
    if (score > 0) {
      ScoreHist scoreHist = new ScoreHist();
      scoreHist.setUserInfo(userInfo);
      scoreHist.setDelng(delng);
      scoreHist.setScoreAmt(score);
      scoreHistService.create(scoreHist);
    }


    // 스탬프
    if (stamp > 0) {
      StampHist stampHist = new StampHist();
      stampHist.setUserInfo(userInfo);
      stampHist.setStampHistTypeCd(StampHistTypeCd.RSRV);
      stampHist.setSetleDttm(ZonedDateTime.now());
      stampHist.setCnt(stamp);
//         stampHist.setMrhst(delng.getmr());
      stampHist.setDelng(newDelng);
      stampHistService.create(stampHist);
    }





    // 변경 회원 정보 저장
    userInfoRepository.save(userInfo);

    // 거래 시 포인트 적립규칙 있는지 확인
    for (PointRule pointRule :
        pointRuleRepository.findByMinAmtGreaterThanEqualAndActiveFlIsTrue(delng.getDelngAmt())) {
      // 적립 규칙 있으면 적립
      // kms: TODO 멤버십 포인트 관련 정책 확인 후 구조 정해지면 개발
    }

    // 결제 정보 처리
    if (delng.getCpnId() != null) {
      Cpn cpn = cpnRepository.findById(delng.getCpnId()).get();
      cpn.setCpnSttsCd(CpnSttsCd.USED);
    }

//    if (newDelng.getDelngPaymentList() != null) {
//      for (DelngPayment delngPayment: newDelng.getDelngPaymentList()) {
//        switch (delngPayment.getDelngPaymentTypeCd()) {
//          case PRPAY:
//            // TODO 선불카드 잔액 차감
//            break;
//          case CPN: // 금액 쿠폰 사용 처리
//            Cpn cpn = delngPayment.getCpn();
//            cpn.setCpnSttsCd(CpnSttsCd.USED);
//            cpnRepository.save(cpn);
//            break;
//          case PG_PAY:
//            // TODO PG 결제 처리
//            break;
//        }
//      }
//    }

    // 운영 중 거래 식별을 위해 서버에서 생성하는 거래 번호
    newDelng.setConfmNo("D" + StringUtils.leftPad("" + delng.getId(), 11, "0"));

    return delngrRepository.save(newDelng);
  }

  @Transactional
  public void delete(Delng delng) {
    // 취소 일시 입력
    delng.setCancelRegDttm(ZonedDateTime.now());

    // 쿠폰 상태 복원
    if (delng.getCpnId() != null) {
      Cpn cpn = cpnRepository.findById(delng.getCpnId()).get();
      if (cpn.getCpnIssu().getValidEndDttm().isAfter(ZonedDateTime.now())) {
        // 유효기간이 남아있다면
        cpn.setCpnSttsCd(CpnSttsCd.AVAIL);
      } else {
        // 유효기간이 남아있지 않다면. 지난 거래 정정할 가능성도 있으므로 사용가능 상태 복원
        cpn.setCpnSttsCd(CpnSttsCd.AVAIL);
      }
    }

    // 상품권 상태 복원

    // 선불카드 상태 복원(환불)


    // 스탬프 차감
    StampHist stampHist = stampHistRepository.findByDelng_Id(delng.getId());
    stampHistService.delete(stampHist);

    // 스코어 차감
    ScoreHist scoreHist = scoreHistRepository.findByDelng_Id(delng.getId());
    scoreHistService.delete(scoreHist);

    // 포인트 차감

    // 환불

    delngrRepository.save(delng);
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
