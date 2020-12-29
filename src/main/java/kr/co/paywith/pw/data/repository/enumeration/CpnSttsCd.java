package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum CpnSttsCd implements EnumMapperType {
  INVALID(0, "무효"),
  // kms: 삭제가능. 발급하고 보이지 않아야 할 필요가 있다면 활성
//  ISSD(1, "발급"),
  AVAIL(5, "사용 가능"),
  USED(9, "사용 완료"),
  EXPR(3, "만료");

  private int code;
  private String title;

  CpnSttsCd(int code, String title) {
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