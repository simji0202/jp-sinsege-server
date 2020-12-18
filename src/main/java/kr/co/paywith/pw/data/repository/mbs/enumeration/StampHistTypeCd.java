package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum StampHistTypeCd implements EnumMapperType {
  D_RSRV("직접 적립"),
  RSRV("적립"),
  USE("사용"),
  CPN("쿠폰발급"),
  EXPR("만료");

  private String title;

  StampHistTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
