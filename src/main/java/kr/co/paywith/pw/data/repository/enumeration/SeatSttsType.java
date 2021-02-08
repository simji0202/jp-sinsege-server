package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum SeatSttsType implements EnumMapperType {
  AVAIL("예약가능"),
  UNAVAIL("예약불가"),
  RSRV("예약"),
  ;

  private String title;

  SeatSttsType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
