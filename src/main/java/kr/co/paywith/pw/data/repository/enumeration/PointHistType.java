package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PointHistType implements EnumMapperType {
  PAYMENT("사용"),
  RSRV("적립"),
  EXPR("만료");

  private String title;

  PointHistType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
