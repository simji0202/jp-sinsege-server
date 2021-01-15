package kr.co.paywith.pw.data.repository.mbs.delng;


import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.gcct.GcctRepository;
import kr.co.paywith.pw.data.repository.mbs.goods.GoodsRepository;
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
  private GcctRepository gcctRepository;

  public void validate(DelngDto delngDto, Errors errors) {

    // 팔수값 확인
    ValidatorUtils.checkObjectNull(delngDto.getDelngAmt(), "거래 금액", errors);
    ValidatorUtils.checkObjectNull(delngDto.getDelngTypeCd(), "거래 종류", errors);
//    ValidatorUtils.checkObjectNull(delngDto.getMrhst(), "매장", errors);
//    ValidatorUtils.checkObjectNull(delngDto.getUserInfo(), "회원", errors);

    boolean isReqFromUser = false; // 회원이 한 요청은 금액 조작 여부를 검증해야 한다
    switch (delngDto.getDelngTypeCd()) {// 거래 종류에 따라 구분 처리
      case POS:
      case PW:
        // 매장 요청이면
        isReqFromUser = false;
        break;
      case APP:
        isReqFromUser = true;
        break;
    }

    // 쿠폰 유효성 검증
    if (delngDto.getCpnAmt() > 0) {
      if (delngDto.getCpnId() == null) {
        errors.reject("쿠폰 검증 오류", "쿠폰 정보가 없습니다");
      } else {
        Cpn cpn = cpnRepository.findById(delngDto.getCpnId()).get();
        if (!cpn.isAvail()) { // 사용가능하지 않은 상태라면..
          errors.reject("쿠폰 검증 오류", "쿠폰이 유효하지 않습니다");
        }
      }
    }

//    int goodsDelngAmt = 0; // 상품 목록에서 쿠폰 금액 제외한 합
//    // 상품 확인
//    if (delngDto.getDelngGoodsList() != null) {
//      for (DelngGoods delngGoods: delngDto.getDelngGoodsList()) {
//        // 상품 개수 오류 확인. 금액과 수량 바꿔 보내는 경우 고려
//        ValidatorUtils.checkInteger(delngGoods.getGoodsCnt(), "상품 수량", errors, true, 1, 100);
//
//        // 상품 검증
//        Goods goods;
//        if (delngGoods.getGoods() == null || delngGoods.getGoods().getId() == null) {
//          errors.reject("상품 검증 오류", "상품 정보가 없습니다");
//        } else {
//          goods = goodsRepository.findById(delngGoods.getGoods().getId()).orElse(null);
//          // 상품 정보 있는지 확인
//          if (goods == null) {
//            errors.reject("상품 검증 오류", "상품 정보가 없습니다");
//          } else {
//            if (isReqFromUser && !goods.getGoodsAmt().equals(delngGoods.getGoodsAmt())) {
//              // 회원 효청이면 상품 가격을 확인. 상품 가격을 잘못 보냈다면 오류. 클라이언트에서 조작 가능성 있음
//              errors.reject("상품 검증 오류", "상품 정보가 잘못되었습니다");
//            }
//
//            // 상품 쿠폰 유효성 검증
//            if (delngGoods.getCpnAmt() != null && delngGoods.getCpnAmt() != 0 ) {
//              // 쿠폰 적용 금액이 있다면 cpn이 있어야 하고 금액만큼 사용가능해야 한다
//              if (delngGoods.getCpn() == null || delngGoods.getCpn().getId() == null) {
//                errors.reject("쿠폰 검증 오류", "쿠폰 정보가 없습니다");
//              } else {
//                // 상품권 확인
//                if (delngGoods.getGcct() != null && delngGoods.getGcct().getId() != null) {
//                  Gcct gcct = gcctRepository.findById(delngGoods.getGcct().getId()).orElse(null);
//
//                  if (gcct == null || gcct.getUsedDttm() != null || gcct.getCancelRegDttm() != null) {
//                    // 해당 상품권이 없거나 / 사용했거나 / 취소되었으면 오류
//                    errors.reject("상품권 검증 오류", "유효한 상품권이 없습니다");
//                  }
//                }
//
//                Cpn cpn = cpnRepository.findById(delngGoods.getCpn().getId()).orElse(null);
//                if (cpn == null) {
//                  errors.reject("쿠폰 검증 오류", "쿠폰 정보가 없습니다");
//                } else if (!cpn.getCpnSttsCd().equals(CpnSttsCd.AVAIL)) {
//                  errors.reject("쿠폰 검증 오류", "사용가능한 쿠폰이 아닙니다");
//                } else {
//                  // 사용 가능 쿠폰
//                  if (isReqFromUser) {
//                    // 회원이 요청했다면 상품 금액과 쿠폰 금액 검증
//                    // 매장에서는 금액까지 확인하지 않음
//                    int cpnAmt = 0;
//                    if (cpn.getCpnIssu().getCpnMaster().getCpnRatio() != null) {
//                      // 할인율 설정 되어있으므로, 상품금액과 곱해서 할인금액 설정
//                      cpnAmt = Math.round(cpn.getCpnIssu().getCpnMaster().getCpnRatio() * goods.getGoodsAmt() * delngGoods.getGoodsCnt());
//                    }
//                    if (cpn.getCpnIssu().getCpnMaster().getCpnAmt() != null &&
//                        cpn.getCpnIssu().getCpnMaster().getCpnAmt() < cpnAmt) {
//                      // 최대 할인 금액 설정된 경우 최대할인 금액까지만 할인
//                      cpnAmt = cpn.getCpnIssu().getCpnMaster().getCpnAmt();
//                    }
//                    if (cpnAmt < delngGoods.getCpnAmt()) {
//                      errors.reject("쿠폰 검증 오류", "쿠폰 할인 금액 계산 오류");
//                    }
//                  }
//                }
//              }
//            }
//          }
//        }
//
//        if (delngGoods.getDelngGoodsOptList() != null) {
//          // 옵션 검증
//          for (DelngGoodsOpt delngGoodsOpt: delngGoods.getDelngGoodsOptList()) {
//            // 상품 개수 오류 확인. 금액과 수량 바꿔 보내는 경우 고려
//            ValidatorUtils.checkInteger(delngGoodsOpt.getGoodsCnt(), "상품 옵션 수량", errors, true, 1, 100);
//
//            // 상품 검증
//            Goods goodsOpt;
//            if (delngGoodsOpt.getGoods() == null || delngGoodsOpt.getGoods().getId() == null) {
//              errors.reject("상품 옵션 검증 오류", "상품 옵션 정보가 없습니다");
//            } else {
//           //   goodsOpt = goodsRepository.findById(delngGoodsOpt.getGoods().getId()).orElse(null);
//              // 상품 옵션 정보 있는지 확인
//              if (goodsOpt == null) {
//                errors.reject("상품 옵션 검증 오류", "상품 옵션 정보가 없습니다");
//              } else {
//                if (isReqFromUser && !goodsOpt.getGoodsAmt().equals(delngGoodsOpt.getGoodsAmt())) {
//                  // 회원 효청이면 상품 옵션 가격을 확인. 상품 옵션 가격을 잘못 보냈다면 오류. 클라이언트에서 조작 가능성 있음
//                  errors.reject("상품 옵션 검증 오류", "상품 옵션 정보가 잘못되었습니다");
//                }
//
//                // 상품 옵션 쿠폰 유효성 검증
//                if (delngGoodsOpt.getCpnAmt() != null && delngGoodsOpt.getCpnAmt() != 0 ) {
//                  // 쿠폰 적용 금액이 있다면 cpn이 있어야 하고 금액만큼 사용가능해야 한다
////                  if (delngGoodsOpt.getCpn() == null || delngGoodsOpt.getCpn().getId() == null) {
////                    errors.reject("쿠폰 검증 오류", "쿠폰 정보가 없습니다");
////                  } else {
////                    if (delngGoodsOpt.getGcct() != null && delngGoodsOpt.getGcct().getId() != null) {
////                        Gcct gcct = gcctRepository.findById(delngGoodsOpt.getGcct().getId()).orElse(null);
////
////                        if (gcct == null || gcct.getUsedDttm() != null || gcct.getCancelRegDttm() != null) {
////                          // 해당 상품권이 없거나 / 사용했거나 / 취소되었으면 오류
////                          errors.reject("상품권 검증 오류", "유효한 상품권이 없습니다");
////                        }
////                    }
//
//                    Cpn cpn = cpnRepository.findById(delngGoodsOpt.getCpn().getId()).orElse(null);
//                    if (cpn == null) {
//                      errors.reject("쿠폰 검증 오류", "쿠폰 정보가 없습니다");
//                    } else if (!cpn.getCpnSttsCd().equals(CpnSttsCd.AVAIL)) {
//                      errors.reject("쿠폰 검증 오류", "사용가능한 쿠폰이 아닙니다");
//                    } else {
//                      // 사용 가능 쿠폰
//                      if (isReqFromUser) {
//                        // 회원이 요청했다면 상품 옵션 금액과 쿠폰 금액 검증
//                        // 매장에서는 금액까지 확인하지 않음
//                        int cpnAmt = 0;
//                        if (cpn.getCpnIssu().getCpnMaster().getCpnRatio() != null) {
//                          // 할인율 설정 되어있으므로, 상품 옵션 금액과 곱해서 할인금액 설정
//                          cpnAmt = Math.round(cpn.getCpnIssu().getCpnMaster().getCpnRatio() * goodsOpt.getGoodsAmt() * delngGoodsOpt.getGoodsCnt());
//                        }
//                        if (cpn.getCpnIssu().getCpnMaster().getCpnAmt() != null &&
//                            cpn.getCpnIssu().getCpnMaster().getCpnAmt() < cpnAmt) {
//                          // 최대 할인 금액 설정된 경우 최대할인 금액까지만 할인
//                          cpnAmt = cpn.getCpnIssu().getCpnMaster().getCpnAmt();
//                        }
//                        if (cpnAmt < delngGoodsOpt.getCpnAmt()) {
//                          errors.reject("쿠폰 검증 오류", "쿠폰 할인 금액 계산 오류");
//                        }
//                      }
//                    }
//                  }
//                }
//              }
//            }
//          }
//        }
//      }
//    }
//
//    if (delngDto.getDelngAmt() > 0) {
//      // 상품권+쿠폰 할인 제외하고 결제할 금액(금액 쿠폰, 선불카드, pg 결제)이 있는 경우
//
//      // 결제 관련 검증
//      if (delngDto.getDelngPaymentList() == null || delngDto.getDelngPaymentList().size() == 0) {
//        errors.reject("결제 검증 오류", "결제 금액이 부족합니다");
//      } else {
//        int paymentAmt = 0;
//        for (DelngPayment delngPayment: delngDto.getDelngPaymentList()) {
//          if (delngPayment.getAmt() == null) {
//            errors.reject("결제 검증 오류", "결제 금액 값이 없습니다");
//          } else {
//            paymentAmt += delngPayment.getAmt();
//            switch (delngPayment.getDelngPaymentTypeCd()){
//              case CPN: // 금액 쿠폰
//                break;
//              case PRPAY: // 선불카드
//                // TODO 선불카드 유효기간과 잔액 확인
//                break;
//              case PG_PAY: // PG 결제
//                // TODO PG 결제 사용 여부. 금액 확인
//                break;
//            }
//          }
//        }
//      }
//    }
//
//    // TODO BeginEventDateTime
//    // TODO CloseEnrollmentDateTime
//  }

//  public void validate(DelngUpdateDto delngUpdateDto, Errors errors) {
//
//
//    // TODO BeginEventDateTime
//    // TODO CloseEnrollmentDateTime
//  }

  }

  public void validate(Account currentUser, Delng delng, Errors errors) {
    ValidatorUtils.checkObjectNull(currentUser, "인증", errors);
    if (currentUser.getAdmin() != null && currentUser.getAdmin().getRoles()
        .contains(AdminRole.ADMIN_MASTER) // 전체 관리자
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
