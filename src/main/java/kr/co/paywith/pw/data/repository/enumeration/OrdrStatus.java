package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OrdrStatus enumeration.
 */
@Getter
public enum OrdrStatus implements EnumMapperType {
  ORDERWAIT("주문대기"),
  PAYWAIT("결제대기"),
  PAYCOMP("결제완료"),
  ACCEPT("주문접수"),
  READY("준비완료"),
  DELIV("배송중"),
  EXT_DELIV("외부업체 배송중. 일정 기간 후 자동으로 다음단계"),
  DELIVCOMP("배송완료"),
  COMP("완료"),
  CANCEL("취소"),
  ORDERFAIL("실패"),
  CANCELFAIL("실패"),
  ORDER("주문"),
  VISIT("방문"),
  TKAWY("수거"),
  CHK_REQ("주문내용 확인"),
  WASH("세탁"),
  WASHCOMP("세탁 완료"),
  PACK("포장"),
  RCV_CONFM("물건 수령"),
  RTN("반송"),
  EXT_RTN("외부업체 반송. 일정기간 후 자동으로 다음단계"),
  RTNCOMP("반송완료"),
  CHK_GOODS("상품 확인"),
  DISPUTE("의견 접수 중"),
  REORDER("같은 내용으로 주문 재요청(새 Ordr 있음)"),
  ;

  private String title;

  OrdrStatus(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
