package kr.co.paywith.pw.data.repository.mbs.delng;


import java.time.LocalDateTime;
import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.SeatSttsType;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable.DelngOrdrSeatTimetable;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPaymentDto;
import kr.co.paywith.pw.data.repository.mbs.gcct.GcctRepository;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.goods.GoodsRepository;
import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMaster;
import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMasterRepository;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStock;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStockRepository;
import kr.co.paywith.pw.data.repository.mbs.seatTimetable.SeatTimetable;
import kr.co.paywith.pw.data.repository.mbs.seatTimetable.SeatTimetableRepository;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DelngValidator {

  @Autowired
  private CpnRepository cpnRepository;

  @Autowired
  private GoodsRepository goodsRepository;

  @Autowired
  private GoodsOptMasterRepository goodsOptMasterRepository;

  @Autowired
  private SeatTimetableRepository seatTimetableRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private GoodsStockRepository goodsStockRepository;

  @Autowired
  private GcctRepository gcctRepository;

  public void validate(DelngDto delngDto, Errors errors) {

    // 팔수값 확인
    ValidatorUtils.checkInt(delngDto.getTotalAmt(), "거래 금액", errors, 0, 99999999);
    ValidatorUtils.checkObjectNull(delngDto.getDelngType(), "거래 종류", errors);
    ValidatorUtils.checkObjectNull(delngDto.getMrhstId(), "매장ID", errors);
    ValidatorUtils.checkObjectNull(delngDto.getMrhstNm(), "매장명", errors);
//    ValidatorUtils.checkObjectNull(delngDto.getUserInfo(), "회원", errors);

    boolean isReqFromUser = false; // 회원이 한 요청은 금액 조작 여부를 검증해야 한다
    switch (delngDto.getDelngType()) {// 거래 종류에 따라 구분 처리
      case POS:
      case PW:
        // 매장 요청이면
        isReqFromUser = false;
        break;
      case APP:
        isReqFromUser = true;
        break;
    }

    // 쿠폰이 유효한지 확인.
    // 상품 확인 시 cpn 이 null 이 아니라면 goodsId를 확인하고, 일치하면 OK.
    // cpn을 null로 다시 설정하여 cpn을 중복 확인하지 않도록 한다
    Cpn cpn = null;
    // 쿠폰 유효성 검증
    if (delngDto.getCpnAmt() > 0) {
      if (delngDto.getCpnId() == null) {
        errors.reject("쿠폰 검증 오류", "쿠폰 정보가 없습니다");
      } else {
        cpn = cpnRepository.findById(delngDto.getCpnId()).get();
        if (!cpn.isAvail()) { // 사용가능하지 않은 상태라면..
          errors.reject("쿠폰 검증 오류", "쿠폰이 유효하지 않습니다");
        } else if (cpn.getCpnMaster().getMinUseStdAmt() > delngDto.getTotalAmt()) {
          // 사용 최소 기준 금액 미만일 때
          errors.reject("쿠폰 검증 오류", "쿠폰을 사용할 수 없는 거래금액입니다.");
        }
      }
    }

    int goodsAmt = 0; // 전체 상품 금액
    int clientCpnGoodsAmt = 0; // 쿠폰 적용할 상품 금액

    // 상품 확인
    if (delngDto.getDelngGoodsList() != null) {
      for (DelngGoods delngGoods : delngDto.getDelngGoodsList()) {
        // 상품 개수 오류 확인. 금액과 수량 바꿔 보내는 경우 고려
        ValidatorUtils.checkInteger(delngGoods.getGoodsCnt(), "상품 수량", errors, true, 1, 100);

        // 상품 검증
        Goods goods;
        if (delngGoods.getGoodsId() == null) {
          errors.reject("상품 검증 오류", "상품 정보가 없습니다");
        } else {
          goods = goodsRepository.findById(delngGoods.getGoodsId()).orElse(null);
          // 상품 정보 있는지 확인
          if (goods == null) {
            errors.reject("상품 검증 오류", "상품 정보가 없습니다");
          } else {
            if (isReqFromUser && goods.getGoodsAmt() != delngGoods.getGoodsAmt()) {
              // 회원 요청이면 상품 가격을 확인. 상품 가격을 잘못 보냈다면 오류. 클라이언트에서 조작 가능성 있음
              errors.reject("상품 검증 오류", "상품 가격이 잘못되었습니다");
            } else {
              goodsAmt += goods.getGoodsAmt() * delngGoods.getGoodsCnt();
            }


            if (delngDto.getDelngDeliv() != null) {
              // 모바일 주문 배송일 때
              if (goods.getMngrStockFl()) { // 재고 관리하는 상품이면
                // 재고 확인
                GoodsStock goodsStock = goodsStockRepository.findByGoods_IdAndMrhst_Id(
                    goods.getId(), delngDto.getMrhstId()
                ).orElse(null);
                if (goodsStock == null || goodsStock.getCnt() < delngGoods.getGoodsCnt()) {
                  // 재고 수 보다 주문 수가 많으면 오류
                  errors.reject("상품 개수 오류", "재고수가 부족합니다");
                }
              }
            }


            // 상품 쿠폰 유효성 검증
            if (cpn != null &&
                clientCpnGoodsAmt == 0 &&
                goods.getId().equals(cpn.getCpnMaster().getGoodsId())
            ) {
              // 쿠폰이 적용되는 상품이 맞음
              clientCpnGoodsAmt = goods.getGoodsAmt() * delngGoods.getGoodsCnt();
            }

            // 상품권 확인
//            if (delngGoods.getGcct() != null && delngGoods.getGcct().getId() != null) {
//              Gcct gcct = gcctRepository.findById(delngGoods.getGcct().getId()).orElse(null);
//
//              if (gcct == null || gcct.getUsedDttm() != null || gcct.getCancelRegDttm() != null) {
//                // 해당 상품권이 없거나 / 사용했거나 / 취소되었으면 오류
//                errors.reject("상품권 검증 오류", "유효한 상품권이 없습니다");
//              }
//            }

            // 옵션 가격 검증
            if (delngGoods.getDelngGoodsOptList() != null) {
              for (DelngGoodsOpt delngGoodsOpt : delngGoods.getDelngGoodsOptList()) {
                for (DelngGoodsOptMaster delngGoodsOptMaster : delngGoodsOpt
                    .getGoodsOptMasterList()) {
                  GoodsOptMaster goodsOptMaster =
                      goodsOptMasterRepository.findById(delngGoodsOptMaster.getId()).get();
                  if (isReqFromUser && goodsOptMaster.getGoodsOptAmt() != delngGoodsOptMaster
                      .getGoodsOptAmt()) {
                    // 회원 요청이면 상품 가격을 확인. 상품 가격을 잘못 보냈다면 오류. 클라이언트에서 조작 가능성 있음
                    errors.reject("상품 검증 오류", "옵션 가격이 잘못되었습니다");
                  } else {
                    // 옵션 가격 정상이므로 합산금액에 더함
                    goodsAmt += delngGoodsOptMaster.getGoodsOptAmt();
                  }
                }
              }
            }
          }
        }
      }

      if (isReqFromUser && delngDto.getTotalAmt() < goodsAmt) {
        // 목록의 상품 금액보다 totalAmt가 적음
        errors.reject("거래 검증 오류", "금액 정보에 오류가 있습니다");
      }

      if (cpn != null && cpn.getCpnMaster().getGoodsId() == null) {
        // 단순 금액 할인 쿠폰이면 delngAmt 확인해서 cpnAmt 가산
        clientCpnGoodsAmt = goodsAmt;
      }

      if (clientCpnGoodsAmt > 0) {
        // 쿠폰 검증해야 할 금액 있음
        switch (cpn.getCpnMaster().getCpnMasterType()) {
          case RATIO: // 비율 쿠폰
            // 상한 확인
            if (cpn.getCpnMaster().getMaxCpnAmt()
                < clientCpnGoodsAmt * cpn.getCpnMaster().getCpnRatio() / 100) {
              errors.reject("쿠폰 검증 오류", "최대할인 금액을 초과했습니다");
            }
            break;
          case AMT:
            if (cpn.getCpnMaster().getCpnAmt() < clientCpnGoodsAmt) {
              errors.reject("쿠폰 검증 오류", "할인 금액을 초과했습니다");
            }
            break;
        }
      }

      if (delngDto.getTotalAmt() - delngDto.getCpnAmt() > 0) {
        // 상품권+쿠폰 할인 제외하고 결제할 금액(금액 쿠폰, 선불카드, pg 결제)이 있는 경우

        // 결제 관련 검증
        int paymentAmt = 0;
        if (delngDto.getDelngPaymentList() == null || delngDto.getDelngPaymentList().size() == 0) {
          errors.reject("결제 검증 오류", "결제 금액이 부족합니다");
        } else {
          for (DelngPaymentDto delngPaymentDto : delngDto.getDelngPaymentList()) {
            if (delngPaymentDto.getAmt() == null) {
              errors.reject("결제 검증 오류", "결제 금액 값이 없습니다");
            } else {
              paymentAmt += delngPaymentDto.getAmt();
              UserInfo userInfo;
              switch (delngPaymentDto.getDelngPaymentType()) {
                case PRPAY: // 선불카드
                  userInfo = userInfoRepository.findById(delngPaymentDto.getUserInfoId()).get();
                  // 선불카드 유효기간과 잔액 확인
                  if (userInfo.getUserCard().getPrpayAmt() < delngDto.getTotalAmt() - delngDto
                      .getCpnAmt()) {
                    // 잔액이 부족하면
                    errors.reject("선불카드 검증 오류", "선불카드 잔액이 부족합니다");
                  }
                  break;
                case PG_PAY: // PG 결제
                  // TODO PG 결제 사용 여부. 금액 확인
                  break;
                case POINT: // 포인트
                  // 현재 포인트가 충분해야 한다
                  userInfo = userInfoRepository.findById(delngPaymentDto.getUserInfoId()).get();
                  if (userInfo.getUserCard().getPointAmt() < delngPaymentDto.getAmt()) {
                    errors.reject("포인트 검증 오류", "포인트 잔액이 부족합니다");
                  }
                  break;
              }
            }
          }
        }

        if (paymentAmt < delngDto.getTotalAmt() - delngDto.getCpnAmt()) {
          // 결제하는 금액이 부족한 경우
          errors.reject("결제 금액 오류", "결제 금액이 부족합니다");
        }
      }
    }

    if (delngDto.getDelngOrdr() != null) {
      // 모바일 주문일 때

      if (delngDto.getDelngOrdr().getDelngOrdrSeatTimetable() != null) {
        // 시트 예약인 경우
        DelngOrdrSeatTimetable delngOrdrSeatTimetable = delngDto.getDelngOrdr()
            .getDelngOrdrSeatTimetable();

        for (SeatTimetable seatTimetable : delngOrdrSeatTimetable.getSeatTimetableList()) {
          SeatTimetable seatTimetable1 = seatTimetableRepository.findById(seatTimetable.getId())
              .get();
          if (!seatTimetable1.getSeatSttsType().equals(SeatSttsType.AVAIL)) {
            errors.reject("기예약 오류", "이미 예약되어 주문할 수 없습니다");
          }

          if (delngOrdrSeatTimetable.getStaffId() != null) {
            // TODO 해당 시간 스태프 상태 확인
          }
        }


      }
    }

    if (delngDto.getDelngDeliv() != null) {
      // 모바일 주문 배송일 때

    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }
//  public void validate(DelngUpdateDto delngUpdateDto, Errors errors) {
//
//
//    // TODO BeginEventDateTime
//    // TODO CloseEnrollmentDateTime
//  }
//  }

  public void validateAccept(Account currentUser, Delng delng, Errors errors) {
    ValidatorUtils.checkObjectNull(currentUser, "인증", errors);
    if ((currentUser.getMrhstTrmnl() != null && currentUser.getMrhstTrmnl().getMrhst().getId()
        .equals(delng.getMrhstId())) // 주문한 매장
    ) {

    } else {
      // 적절한 권한이 없으면 오류
      errors.reject("권한 없음", "삭제 권한이 없습니다");
    }

    if (delng.getDelngOrdr() == null) { // 모바일 주문만 접수 -> 완료 가능
      errors.reject("설정 오류", "접수가 불가능한 주문 형태입니다");
    } else if (delng.getDelngOrdr().getCancelDttm() != null) {
      errors.reject("접수 오류", "이미 취소한 주문입니다");
    } else if (delng.getDelngOrdr().getAcceptDttm() != null) {
      errors.reject("접수 오류", "이미 접수한 주문입니다");
    }
  }

  public void validateComp(Account currentUser, Delng delng, Errors errors) {
    ValidatorUtils.checkObjectNull(currentUser, "인증", errors);
    if ((currentUser.getMrhstTrmnl() != null && currentUser.getMrhstTrmnl().getMrhst().getId()
        .equals(delng.getMrhstId())) // 주문한 매장
    ) {

    } else {
      // 적절한 권한이 없으면 오류
      errors.reject("권한 없음", "취소 권한이 없습니다");
    }

    // TODO 후불결제 있는 거래면 후불 결제 완료 여부 확인

    if (delng.getDelngOrdr() == null) { // 모바일 주문만 접수 -> 완료 가능
      errors.reject("설정 오류", "완료가 불가능한 주문 형태입니다");
    } else if (delng.getDelngOrdr().getCancelDttm() != null) {
      errors.reject("완료 오류", "이미 취소한 주문입니다");
    } else if (delng.getDelngOrdr().getCompDttm() != null) {
      errors.reject("완료 오류", "이미 완료한 주문입니다");
    }
  }

  public void validate(Account currentUser, Delng delng, Errors errors) {
    ValidatorUtils.checkObjectNull(currentUser, "인증", errors);
    if (currentUser.getAdmin() != null && currentUser.getAdmin().getRoles()
        .contains(AdminRole.ADMIN_MASTER) || // 전체 관리자
        (currentUser.getMrhstTrmnl() != null && currentUser.getMrhstTrmnl().getMrhst().getId()
            .equals(delng.getMrhstId())) || // 주문한 매장
        (currentUser.getUserInfo() != null && currentUser.getUserInfo().getId()
            .equals((delng.getUserInfo().getId())) &&
            delng.getDelngOrdr() != null && delng.getDelngOrdr().getAcceptDttm() == null
            && delng.getDelngOrdr().getCompDttm() == null // 회원은 접수되지 않은 거래만 취소 가능
        )
    ) {

    } else {
      // 적절한 권한이 없으면 오류
      errors.reject("권한 없음", "삭제 권한이 없습니다");
    }

    // 이미 취소한 거래면 오류
    if (delng.getCancelRegDttm() != null) {
      errors.reject("취소 오류", "이미 취소한 거래");
    }
  }
}
