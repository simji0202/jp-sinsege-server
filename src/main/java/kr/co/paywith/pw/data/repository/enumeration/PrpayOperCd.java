package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 선불카드 제공(구분) 코드
 */
@Getter
public enum PrpayOperCd implements EnumMapperType {
  A("앱 카드"),
  M("마그네틱");

  private String title;

  PrpayOperCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
