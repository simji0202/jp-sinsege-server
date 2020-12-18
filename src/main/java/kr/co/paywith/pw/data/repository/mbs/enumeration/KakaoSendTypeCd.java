package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum KakaoSendTypeCd implements EnumMapperType {

  WITHDRAW("회원 탈회"),
  HISTORY("거래 내역"),
  CPN_USE("쿠폰 사용"),
  CPN_ISSU("쿠폰 발급");

  private String title;

  KakaoSendTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
