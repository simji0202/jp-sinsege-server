package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum DelngPaymentType implements EnumMapperType {
  PRPAY("선불카드"),
  POINT("포인트"),
  PG_PAY("PG 결제"),
  DFPAY("후불결제. 오더에서 사용 comp 전엔 결제 확인 필요"),
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
