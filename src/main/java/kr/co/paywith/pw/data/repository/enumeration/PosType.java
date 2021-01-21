package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PosType implements EnumMapperType {
  OKPOS("OKPOS"),
  SMTR("Smatro"),
  KIOSK("KIOSK"),
  PW("PayWith"),
  EASY("KICC EASY POS");

  private String title;

  PosType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
