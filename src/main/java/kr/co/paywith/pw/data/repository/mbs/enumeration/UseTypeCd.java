package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum UseTypeCd implements EnumMapperType {
  P("선불카드"),
  C("쿠폰"),
  S("스탬프"),
  A("앱"),
  N("포인트");

  private String title;

  UseTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
