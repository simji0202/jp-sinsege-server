package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum ChrgSetleChnlCd implements EnumMapperType {
  APP("앱"),
  NICE("NICE PG"),
  KICC("KICC PG"),
  KICC_D("KICC PG 바로 결제"), // 빌링 키 이용해서 잔액 남기기 않고 사용처리 될 경우에만 사용
  SYS("시스템"), // 카드 병합 충전
  STR("매장"),
  OKPOS("OKPOS"),
  SMTR("Smatro"),
  EASY("KICC EASY POS"),
  CFBY("기존 커피베이에서 변환"),
  ADMIN("관리자"),
  PW("웹");

  private String title;

  ChrgSetleChnlCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
