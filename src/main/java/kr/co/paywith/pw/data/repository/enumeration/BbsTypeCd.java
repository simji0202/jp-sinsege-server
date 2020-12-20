package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum BbsTypeCd implements EnumMapperType {
  NTC("공지사항"),
  MSG("알림"),
  FAQ("FAQ"),
  QNA("Q&A"),
  POPUP("팝업"),
  EVNT("이벤트"),
  BANNER("배너"),
  W_POS("POS공지");

  private String title;

  BbsTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
