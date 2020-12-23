package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 브랜드에서 사용가능한 기능 목록
 */
@Getter
public enum PlatformTypeCd implements EnumMapperType {

  FLUT("Flutter App"),
  ELEC("Electron App"),
  WEB("Web Browser Common");

  private String title;

  PlatformTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
