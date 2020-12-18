package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PrpayHistTypeCd implements EnumMapperType {
  C("충전"),
  U("사용"),
  C_C("충전취소"),
  U_C("사용취소");

  private String title;

  PrpayHistTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
