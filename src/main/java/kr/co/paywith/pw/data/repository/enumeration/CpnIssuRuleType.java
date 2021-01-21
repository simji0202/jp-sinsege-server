package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 쿠폰 발급 규칙 코드
 */
@Getter
public enum CpnIssuRuleType implements EnumMapperType {
  // 시간과 관계 없는 조건
  CHRG("stdValue 이상 금액 충전"),
  PAYMENT("stdValue 이상 결제"), // DelngPayment 금액
  GOODS("goods.id 구매"),
  JOIN("회원 가입"),
  // 시간 일치 조건
  STAMP("스탬프 적립 달성"),
  SCORE("stdValue 이상 점수"),
  BD("생일 stdValue 일 이전. D-stdValue 일 때 발급");

  private String title;

  CpnIssuRuleType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public String getTitle() {
    return title;
  }
}
