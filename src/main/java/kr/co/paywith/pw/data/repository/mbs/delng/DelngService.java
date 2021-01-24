package kr.co.paywith.pw.data.repository.mbs.delng;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.time.ZonedDateTime;
import java.util.List;
import javax.transaction.Transactional;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsType;
import kr.co.paywith.pw.data.repository.enumeration.PointCutType;
import kr.co.paywith.pw.data.repository.enumeration.PointHistType;
import kr.co.paywith.pw.data.repository.enumeration.PointRsrvRuleType;
import kr.co.paywith.pw.data.repository.enumeration.StampHistType;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPaymentDto;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.goods.GoodsRepository;
import kr.co.paywith.pw.data.repository.mbs.pointHist.PointHist;
import kr.co.paywith.pw.data.repository.mbs.pointHist.PointHistService;
import kr.co.paywith.pw.data.repository.mbs.pointRsrvRule.PointRsrvRule;
import kr.co.paywith.pw.data.repository.mbs.pointRsrvRule.PointRsrvRuleRepository;
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
  private PointRsrvRuleRepository pointRsrvRuleRepository;

  @Autowired
  private GoodsRepository goodsRepository;

  @Autowired
  private StampHistRepository stampHistRepository;

  @Autowired
  private ScoreHistRepository scoreHistRepository;

  @Autowired
  private ScoreHistService scoreHistService;

  @Autowired
  private PointHistService pointHistService;

  @Autowired
  private Gson gson;

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
        score += goods.getScorePlus() * delngGoods.getGoodsCnt();

        // 스탬프 가산
        stamp += goods.getStampPlusCnt() + delngGoods.getGoodsCnt();
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
      stampHist.setStampHistType(StampHistType.RSRV);
      stampHist.setSetleDttm(ZonedDateTime.now());
      stampHist.setCnt(stamp);
      stampHist.setDelng(newDelng);
      stampHistService.create(stampHist);
    }

    if (delngDto.getDelngPaymentList() != null) {
      for (DelngPaymentDto delngPayment : delngDto.getDelngPaymentList()) {
        switch (delngPayment.getDelngPaymentType()) {
          case PRPAY:
            // 선불카드 잔액 차감
            userInfo.getUserCard()
                .setPrpayAmt(userInfo.getUserCard().getPrpayAmt() - delngPayment.getAmt());
            break;
          case POINT:
            // 포인트 잔액 차감
            PointHist pointHist = new PointHist();
            pointHist.setUserInfo(userInfo);
            pointHist.setPointHistType(PointHistType.PAYMENT);
            pointHist.setPointAmt(-1 * delngPayment.getAmt()); // 포인트 사용은 음수
            pointHist.setDelng(newDelng);
            pointHistService.create(pointHist);

            userInfo.getUserCard()
                .setPointAmt(userInfo.getUserCard().getPointAmt() - delngPayment.getAmt());
            break;
          case PG_PAY:
            // TODO PG 결제 처리
//            payAmt += delngPayment.getAmt();
            break;
        }
      }
    }

    // 변경 회원 정보 저장
    userInfoRepository.save(userInfo);

    // 거래 시 포인트 적립규칙 있는지 확인
    for (PointRsrvRule pointRsrvRule :
        pointRsrvRuleRepository.findByPointRsrvRuleTypeAndStdValueGreaterThanEqualAndActiveFlIsTrue(
            PointRsrvRuleType.PAYMENT, delng.getPaymentAmt())) {
      // 적립 규칙 있으면 적립
      // kms: TODO 멤버십 포인트 관련 정책 확인 후 구조 정해지면 개발
      int point = 0;
      switch (pointRsrvRule.getPointCutType()) {
        case F:
          point = (int) Math.floor(pointRsrvRule.getRsrvRatio() * delng.getPaymentAmt());
          break;
        case C:
          point = (int) Math.ceil(pointRsrvRule.getRsrvRatio() * delng.getPaymentAmt());
          break;
        default:
          point = (int) Math.round(pointRsrvRule.getRsrvRatio() * delng.getPaymentAmt());
          break;
      }

      if (point > 0) {
        PointHist pointHist = new PointHist();
        pointHist.setUserInfo(userInfo);
        pointHist.setPointHistType(PointHistType.RSRV);
        pointHist.setPointAmt(point);
        pointHist.setDelng(newDelng);
        pointHistService.create(pointHist);
      }

    }

    // 결제 정보 처리
    // 쿠폰 정보 처리
    if (delng.getCpnId() != null) {
      Cpn cpn = cpnRepository.findById(delng.getCpnId()).get();
      cpn.setCpnSttsType(CpnSttsType.USED);
    }

    // TODO 상품권 사용 처리

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
        cpn.setCpnSttsType(CpnSttsType.AVAIL);
      } else {
        // 유효기간이 남아있지 않다면. 지난 거래 정정할 가능성도 있으므로 사용가능 상태 복원
        cpn.setCpnSttsType(CpnSttsType.AVAIL);
      }
    }

    // 스탬프 차감
    StampHist stampHist = stampHistRepository.findByDelng_Id(delng.getId());
    if (stampHist != null) {
      stampHistService.delete(stampHist);
    }

    // 스코어 차감
    ScoreHist scoreHist = scoreHistRepository.findByDelng_Id(delng.getId());
    if (scoreHist != null) {
      scoreHistService.delete(scoreHist);
    }

    // 결제 관련 정보 복원
    // 결제 상품 정보를  Json 데이터에서 객체화
    if (delng.getDelngPaymentJson() != null) {
      List<DelngPaymentDto> delngPaymentDtos =
          gson.fromJson(delng.getDelngPaymentJson(), new TypeToken<List<DelngPaymentDto>>() {
          }.getType());

      for (DelngPaymentDto delngPaymentDto : delngPaymentDtos) {
        UserInfo userInfo = delng.getUserInfo();
        switch (delngPaymentDto.getDelngPaymentType()) {
          case PRPAY:
            // 선불카드 상태 복원(환불)
            userInfo.getUserCard()
                .setPrpayAmt(userInfo.getUserCard().getPrpayAmt() + delngPaymentDto.getAmt());
            userInfoRepository.save(userInfo);
            // TODO 환불
            // 환불 기능 필요성 검토 후 개발
            break;
          case PG_PAY:
            // TODO PG 환불
            break;
          case POINT:
            // 포인트 상태 복원(환불)
            userInfo.getUserCard()
                .setPointAmt(userInfo.getUserCard().getPointAmt() + delngPaymentDto.getAmt());
            userInfoRepository.save(userInfo);
            break;
        }
      }
    }

    // TODO 상품권 상태 복원

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
