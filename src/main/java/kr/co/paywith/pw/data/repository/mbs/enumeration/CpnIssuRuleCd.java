package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

/**
 * 쿠폰 발급 규칙 코드
 */
@Getter
public enum CpnIssuRuleCd implements EnumMapperType {
  AC("총 충전 금액"),
  AU("총 사용 금액"),
  C("충전 금액"),
  U("사용 금액"),
  B("특정 상품 구매"),
  DTTM("날짜시간 일치"),
  J("회원 가입"),
  BD("생일"),
  S("스탬프 적립"),
  SI("스탬프적립 즉시"),
  GU("등급 상승");

  private String title;

  CpnIssuRuleCd(String title) {
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
