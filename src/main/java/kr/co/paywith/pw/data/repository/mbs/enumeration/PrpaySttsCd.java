package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PrpaySttsCd implements EnumMapperType {
  AVAIL(1, "사용가능"),
  RGST(2, "등록"),
  USED(3, "사용 중"),
  STOP(4, "정지"),
  MRGD(5, "병합됨");

  private int code;
  private String title;

  PrpaySttsCd(int code, String title) {
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
