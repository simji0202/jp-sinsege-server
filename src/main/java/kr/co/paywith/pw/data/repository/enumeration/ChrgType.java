package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum ChrgType implements EnumMapperType {
  PW("페이위드 단말기. aka 웹포스"),
  APP("모바일 충전"),
  POS("포스 단말기 충전"),
  SYS("시스템. 관리자 또는, 테스트 목적"),
  ;

  private String title;

  ChrgType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
