package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PwSttsType implements EnumMapperType {
  COMP("정상완료"),
  BF("PG 요청 전"),
  AF("PG 요청 직후"),
  MISMATCH("정보 미일치"),
  CANCEL("취소");

  private String title;

  PwSttsType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
