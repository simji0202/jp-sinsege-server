package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum DelngType implements EnumMapperType {
  PW("페이위드 단말기. aka 웹포스"),
  APP("모바일 주문"),
  POS("포스 단말기 거래"),
  ;

  private String title;

  DelngType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
