package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum DelngPaymentTypeCd implements EnumMapperType {
  PRPAY("선불카드"),
  PG_PAY("PG 결제"),
  CPN("금액 쿠폰"),
  ;

  private String title;

  DelngPaymentTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
