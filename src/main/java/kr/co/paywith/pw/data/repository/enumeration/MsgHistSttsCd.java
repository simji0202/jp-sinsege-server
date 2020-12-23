package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum MsgHistSttsCd implements EnumMapperType {
  WT("대기"),
  SN("완료"),
  FL("실패");

  private String title;

  MsgHistSttsCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
