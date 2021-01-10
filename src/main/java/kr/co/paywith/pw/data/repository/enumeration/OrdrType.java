package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OrdrType enumeration.
 */
@Getter
public enum OrdrType implements EnumMapperType {
  IN("매장방문식사"), OUT("매장방문포장"), DELIV("배송"), RSRV("예약"),
  PICK_DELIV("픽업과 배송");

  private String title;

  OrdrType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
