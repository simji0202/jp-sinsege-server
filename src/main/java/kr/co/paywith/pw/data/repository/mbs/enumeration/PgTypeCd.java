package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PgTypeCd implements EnumMapperType {
  NICE("NICE PG", "NICE"),
  KICC("KICC PG 선불충전", "KICC"),
  KICC_D("KICC PG 직접결제", "KICC");

  private String title;
  private String pwProxyCd; // KICC_D 도 proxy 에는 KICC를 전달해야 하므로, 별도 필드 생성

  PgTypeCd(String title, String pwProxyCd) {
    this.title = title;
    this.pwProxyCd = pwProxyCd;
  }

  @Override
  public String getCode() {
    return name();
  }

}
