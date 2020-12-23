package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PwPgType implements EnumMapperType {
  KICC("KICC"),
  NICE("NICE");

  private String title;

  PwPgType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
