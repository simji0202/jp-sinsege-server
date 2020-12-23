package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PosAvailFnCd implements EnumMapperType {
  CHRG("충전"),
  CHRG_HIST("충전 이력"),
  USE("사용"),
  USE_HIST("사용 이력"),
  ORDR("주문"),
  STAMP("스탬프"),
  STAMP_HIST("스탬프 이력"),
  POINT("포인트"),
  TIMESALE("할인"),
  MNGR("관리"),
  BBS("소통 페이지"),
  NTC("공지사항 작성"),
  QNA("문의에 대한 답변"),
  MNGR_ORDR("주문 설정"),
  MNGR_MRHST("매장 정보 관리");

  private String title;

  PosAvailFnCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
