package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PointRuleTypeCd  implements EnumMapperType {
  REG("카드등록"),
  C("충전"),
  U("사용");

  private String title;

  PointRuleTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
