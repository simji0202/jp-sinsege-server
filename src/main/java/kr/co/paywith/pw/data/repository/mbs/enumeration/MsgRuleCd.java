package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum MsgRuleCd implements EnumMapperType {
  A_JOIN("회원 가입 후"),
  B_GRD_DN_7("등급 다운 7일 전"),
  A_FIND_ID("아이디 찾기 후"),
  A_GIFT("선물하기 후"),
  B_CPN_E_1("쿠폰 만료 1일 전"),
  B_CPN_E_7("쿠폰 만료 7일 전"),
  C_BILLING("정기결제 확인");

  private String title;

  MsgRuleCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
