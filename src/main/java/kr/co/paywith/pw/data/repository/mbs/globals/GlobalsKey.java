package kr.co.paywith.pw.data.repository.mbs.globals;

import lombok.Getter;

@Getter
public enum GlobalsKey {
  CHRG_SN("일일 충전 일련번호"),
  USE_PRPAY_SN("일일 선불카드 사용 일련번호"),
  USE_CPN_SN("일일 쿠폰 사용 일련번호"),
  USE_STAMP_SN("일일 스탬프 사용 일련번호"),
  USE_PAY_SN("일일 결제 사용 일련번호"),
  PAYMENT_SN("일일 결제 일련번호"),
  POINT_SN("일일 포인트 사용 일련번호");

  private String title;

  GlobalsKey(String title) {
    this.title = title;
  }
}
