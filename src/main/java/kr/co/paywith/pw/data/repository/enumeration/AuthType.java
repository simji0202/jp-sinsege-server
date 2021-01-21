package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum AuthType implements EnumMapperType {
  MST("시스템 관리자"),
  B_MST("브랜드 관리자"),
  S_MST("상점 마스터"),
  USR("일반 관리자"),
  NO_AUTH("접근 권한 없음");

  private String title;

  AuthType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
