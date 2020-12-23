package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum GradeUpRuleCd implements EnumMapperType {
  END("변경 없음"),
  CP("총 획득 포인트"),
  UP("총 사용 포인트"),
  SC("총 획득 점수"),
  CS("현재 점수"),
  CA("총 충전 금액"),
  UA("총 사용 금액"),
  RG("카드 등록 시"),
  SG("총 적립 스탬프"),
  SU("총 사용 스탬프");

  private String title;

  GradeUpRuleCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
