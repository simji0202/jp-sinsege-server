package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The UserType enumeration.
 */
@Getter
public enum StatProcType implements EnumMapperType {
  GENERAL("일반. 단방향"),
  BIDRC("양방향"), // 전단계로 갈 수 있음
  COMP("완료"), // 취소만 가능. 전체 흐름에서 한번만 가능
  @Deprecated
  CANCEL("취소"), // 완료 전이면 거래만 취소. 완료 후면 정산기준 쪽 취소일시도 기록 후 정산취소 해야 함. 결제가 있으면 이후 상태 변경 절대 불가
  CHK_PAY("결제 금액 일치 확인"), //  이 단계로 넘어올 때 결제금액 = 상품금액이면 다음 단계로 바로 변경. 다음 단계가 하나 있거나, 다음 단계 중 PAYCOMP 있을 때 변경
  ;


  private String title;

  StatProcType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
