package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PwGoodsType implements EnumMapperType {
  CONT("컨텐츠"),
  REAL("실물");

  private String title;

  PwGoodsType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
