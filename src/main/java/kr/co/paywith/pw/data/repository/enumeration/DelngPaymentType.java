package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum DelngPaymentType implements EnumMapperType {
  PRPAY("선불카드"),
  POINT("포인트"),
  PG_PAY("PG 결제"),
  ;

  private String title;

  DelngPaymentType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
