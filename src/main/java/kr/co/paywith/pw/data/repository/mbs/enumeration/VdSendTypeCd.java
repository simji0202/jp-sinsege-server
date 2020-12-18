package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum VdSendTypeCd  implements EnumMapperType {

  REG("등록(activate)"),
  CHRG("충전"),
  USE("이용"),
  CHRG_C("충전 취소"),
  RTN("반품"),
  USE_C("이용 취소"),
  GET("잔액 조회"),
  CHRG_C2("충전 취소2");

  private String title;

  VdSendTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
