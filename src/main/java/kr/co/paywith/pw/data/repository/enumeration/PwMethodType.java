package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 페이위드에서 분류하는 결제방법
 */
@Getter
public enum PwMethodType implements EnumMapperType {
  CARD("신용카드"),
  BANK("계좌이체"),
  CELLPHONE("휴대폰결제"),
  BILL("빌키 발급"),
  BILL_P("빌키로 결제"),
  VBANK("가상계좌");

  private String title;

  PwMethodType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
