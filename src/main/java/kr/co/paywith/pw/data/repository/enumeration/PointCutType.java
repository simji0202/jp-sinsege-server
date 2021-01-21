package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PointCutType implements EnumMapperType {
  F("올림"),
  R("반올림"),
  C("버림");

  private String title;

  PointCutType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
