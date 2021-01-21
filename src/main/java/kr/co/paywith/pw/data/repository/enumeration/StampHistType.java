package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum StampHistType implements EnumMapperType {
  D_RSRV("직접 적립"),
  RSRV("적립"),
  // 스탬프 직접 사용은 막을예정. pos에서 사용 전문은 있기 때문에 enum만 유지(처리 로직 미 구현)
  @Deprecated
  USE("사용"),
  CPN("쿠폰발급"),
  EXPR("만료");

  private String title;

  StampHistType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
