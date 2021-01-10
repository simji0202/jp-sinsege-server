package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The PayType enumeration.
 */
@Getter
public enum PayType implements EnumMapperType {
  PPCD("선불카드"),
//  KAKAO("카카오페이"),
//  PHONE("휴대폰결제"),
  CPN("쿠폰결제"),
//  CARD("신용카드"),
  PW_PAY("PW_결제"),
  PW_POINT("PW_포인트");

  private String title;

  PayType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
