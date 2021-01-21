package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OrdrStatus enumeration.
 */
@Getter
public enum GoodsOpt implements EnumMapperType {
  CHOICE("선택"),
  ADD("추가"),

  ;


  private String title;

  GoodsOpt(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
