package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OrdrStatus enumeration.
 */
@Getter
public enum GoodsOptMasterCd implements EnumMapperType {
  CHOICE("선택"),
  ADD("추가"),

  ;


  private String title;

  GoodsOptMasterCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
