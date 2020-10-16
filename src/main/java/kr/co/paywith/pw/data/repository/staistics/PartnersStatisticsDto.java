package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class PartnersStatisticsDto {

  @NameDescription("신규가입 수 ")
  private int newpartnerCount;

  @NameDescription("영업승인업체수")
  private int businessCount;

  @NameDescription("잔액부족업체수")
  private int insufficientCount;

  @NameDescription("일시정지업체수")
  private int temporarystopCount;

  @NameDescription("보류")
  private int suspensionCount;

  @NameDescription("탈퇴")
  private int withdrawalCount;

}
