package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum DtTypeCd implements EnumMapperType {
  Y("연"),
  M("월"),
  D("일"),
  H("시"),
  MN("분"),
  S("초");

  private String title;

  DtTypeCd(String title) {
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
