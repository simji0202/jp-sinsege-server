package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

/**
 * 스탬프 상태(DB는 효율화를 위하여 index 숫자로 저장한다(신규 값이 있을 경우, 무조건 마지막에 추가)
 */
@Getter
public enum StampSttsTypeCd implements EnumMapperType {
  RSRV("적립"),
  EXPR("만료"), // 사용 여부에 관계없이 기간 지난 스탬프, 삭제 처리하는게 좋아보임
  USE("사용"),
  CPN("CPN");

  private String title;

  StampSttsTypeCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
