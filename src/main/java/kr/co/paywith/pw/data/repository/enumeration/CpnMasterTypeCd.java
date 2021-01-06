package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 쿠폰 마스터 종류
 */
@Getter
public enum CpnMasterTypeCd implements EnumMapperType {
  AMT("금액 쿠폰"),
  GOODS("상품 쿠폰"),
  ;

  private String title;

  CpnMasterTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
