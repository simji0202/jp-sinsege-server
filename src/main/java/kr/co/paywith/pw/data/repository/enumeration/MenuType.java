package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum MenuType implements EnumMapperType {
  NTC("공지사항"),
  QNA("QNA"),
  FAQ("FAQ"),
  EVNT("이벤트"),
  POPUP("팝업 게시물 관리"),
  NOTIF("알림"),
  MSG("메시지"),
  BANNER("배너"),
  PNT("포인트"),
  USER("회원"),
  TRANS("거래이력"),
  ORDR("주문"),
  ORDR_MRHST("매장(주문)"),
  MRHST("매장"),
  MRHST_ST("매장 상태"),
  TRMNL("단말기"),
  RCM_MRHST("매장 추천"),
  CALCU("정산"),
  GIFT("선물"),
  PRPAY("선불카드"),
  PRPAY_ISSU("선불카드 발급"),
  STAT("통계"),
  CPN("쿠폰"),
  CPN_MST("쿠폰 종류"),
  GOODS("상품"),
  TOTAL("사용 내역 종합"),
  W_POS("웹포스 사용"),
  SYS("시스템"),
  CFBY_PREF("커피베이(임시 개발페이지) 선호매장"),
  STAMP("스탬프"),
  PAYMENT("결제");

  private String title;

  MenuType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
