package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum ScoreRuleTypeCd implements EnumMapperType {
  CC("충전 횟수"),
  CA("충전 금액"),
  CPA("선불카드 충전 결제 금액"),
  UC("구매 상품 개수"),
  SCR("구매 상품 점수"),
  UA("구매 금액"),
  UM("구매 횟수 "),
  PA("결제(DelngPayment) 금액"),
  USC("스탬프 개수(자동)"),
  SC("스탬프 개수");

  private String title;

  ScoreRuleTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
