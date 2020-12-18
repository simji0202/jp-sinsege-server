package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PaymentChnlCd implements EnumMapperType {
  NICE("NICE PG"),
  KICC("KICC PG 선불 구매"),
  KICC_D("KICC PG 바로 구매"),
  PW("웹");

  private String title;

  PaymentChnlCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
