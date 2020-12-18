package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum ChrgSetleSttsCd implements EnumMapperType {
  PRE("대기"),
  COM("완료"),
  CAN("취소"), // 현재 미사용
  FAIL("실패");

  private String title;

  ChrgSetleSttsCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
