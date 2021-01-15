package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 쿠폰 마스터 종류
 */
@Getter
public enum CpnMasterTypeCd implements EnumMapperType {
  AMT("금액"),
  GOODS("1+1"),
  RATIO("할인율"),
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
