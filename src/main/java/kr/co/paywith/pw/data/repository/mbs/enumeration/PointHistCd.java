package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PointHistCd implements EnumMapperType {

  RU("사용 → P"),
  RC("충전 → P"),
  REG("등록카드 잔액 → P"),
  RA("관리자 충전"),
  UC("P → 충전"),
  UU("P → 사용"),
  UP("결제구매 → P");

  private String title;

  PointHistCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
