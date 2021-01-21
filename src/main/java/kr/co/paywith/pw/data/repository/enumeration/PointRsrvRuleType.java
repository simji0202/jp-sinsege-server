package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PointRsrvRuleType implements EnumMapperType {
  CHRG("stdValue 이상 금액 충전"),
  PAYMENT("stdValue 이상 결제"), // DelngPayment 금액
  ;

  private String title;

  PointRsrvRuleType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
