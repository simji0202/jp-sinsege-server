package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PosTypeCd implements EnumMapperType {
  STR("매장(일반)"),
  OKPOS("OKPOS"),
  SMTR("Smatro"),
  KIOSK("KIOSK"),
  PW("PayWith"),
  EASY("KICC EASY POS");

  private String title;

  PosTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
