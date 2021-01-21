package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum CpnSttsType implements EnumMapperType {
  INVALID(0, "무효"),
  AVAIL(5, "사용 가능"),
  USED(9, "사용 완료"),
  EXPR(3, "만료");

  private int code;
  private String title;

  CpnSttsType(int code, String title) {
    this.code = code;
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

  public String getValue() {
    return "" + this.code;
  }
}
