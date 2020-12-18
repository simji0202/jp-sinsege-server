package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PaymentSttsCd implements EnumMapperType {
  PRE("대기"),
  COM("완료"),
  FAIL("실패");

  private String title;

  PaymentSttsCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
