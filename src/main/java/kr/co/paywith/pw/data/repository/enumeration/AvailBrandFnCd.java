package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 브랜드에서 사용가능한 기능 목록
 */
@Getter
public enum AvailBrandFnCd implements EnumMapperType {

  PRPAY("선불카드"),
  ORDR("모바일 주문"),
  ORDR_RSRV("모바일 주문"),
  CALCU("정산"),
  CPN("쿠폰"),
  STAMP("스탬프"),
  POINT("포인트"),
  QNA("답변게시판"),
  MRHST_IMG("매장 이미지"),
  KAKAO_M("카카오페이멤버십"),
  PAYMENT("결제"),
  MRHST_CN("매장 설명"),
  OPT_ETC("추가상품"),
  ;


  private String title;

  AvailBrandFnCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
