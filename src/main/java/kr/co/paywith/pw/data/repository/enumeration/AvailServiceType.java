package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum AvailServiceType implements EnumMapperType {

  ALLDAY("24시간 운영"),
  PRPAY("선불카드 사용"),
  WIFI("와이파이"),
  CATERING("캐이터링"),
  SMOKING("흡연석"),
  PARKING("주차장"),
  KIDS("키즈존"),
  BOOK("독서존"),
  POWDER("파우더룸"),
  MEETING("회의실"),
  BIZ("Business Room"),
  ORDR("모바일 주문"),
  ORDR_RSRV("모바일 예약"),
  EVENT("이벤트 룸"),
  DRINK("음주"),
  FD_CHCKN("음식_치킨"),
  FD_PIZZA("음식_피자"),
  FD_CHN("음식_중화요리"),
  FD_LEG("음식_족발"),
  FD_CAFE("음식_카페"),
  FD_DESSERT("음식_디저트"),
  FD_WEST("음식_양식"),
  FD_KR("음식_한식"),
  FD_NIGHT("음식_야식"),
  FD_SOUP("음식_찜/탕"),
  FD_FAST("음식_패스트푸드"),
  FD_BRD("음식_빵집"),
  FD_BRDCF("음식_베이커리카페"),
  FD_DSRTCF("음식_디저트카페"),
  SIZE_S("규모_S"),
  SIZE_M("규모_M"),
  SIZE_L("규모_L"),
  TERRACE("테라스"),
  GROUP("단체석"),
  MD_VIEW("분위기_경관"),
  MD_PARTCLR("분위기_독특"),
  MD_EXOTC("분위기_이국적"),
  MSC("매스컴"),
  MENU_VEGAN("메뉴_비건"),
  MST("전문점"),
  LOCAL("동네"),
  STATION("역세권"),
  NOKIDS("노키즈존"),
  ;
  private String title;

  AvailServiceType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
