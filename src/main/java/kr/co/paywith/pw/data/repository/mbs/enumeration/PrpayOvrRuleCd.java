package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum PrpayOvrRuleCd implements EnumMapperType {
  D("추가 등록 불가"),
  FIRST("오래된 카드 삭제"),
  LAST("최근 카드 삭제"),
  MERGE("최근 카드에 합산");

  private String title;

  PrpayOvrRuleCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
