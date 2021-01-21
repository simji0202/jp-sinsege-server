package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum KakaoSendType implements EnumMapperType {

  WITHDRAW("회원 탈회"),
  HISTORY("거래 내역"),
  CPN_USE("쿠폰 사용"),
  CPN_ISSU("쿠폰 발급");

  private String title;

  KakaoSendType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
