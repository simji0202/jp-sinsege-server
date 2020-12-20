package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum CpnTypeCd implements EnumMapperType {
  AMT("금액 쿠폰"),
  GOODS("상품 쿠폰"),
  OPT("옵션 쿠폰");

  private String title;

  CpnTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
