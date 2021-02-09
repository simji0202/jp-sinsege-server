package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import java.time.LocalTime;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MrhstOrdrDto {

  @NameDescription("예약 단위 분")
  private Integer rsrvUnitMin;

  @NameDescription("야간운영_여부")
  private Boolean nightOpenFl;

  private Boolean ordrFl = false;

  private Boolean useTimetableFl = false;

  @NameDescription("시작시간_월")
  private LocalTime openTm1;

  @NameDescription("시작시간_화")
  private LocalTime openTm2;

  @NameDescription("시작시간_수")
  private LocalTime openTm3;

  @NameDescription("시작시간_목")
  private LocalTime openTm4;

  @NameDescription("시작시간_금")
  private LocalTime openTm5;

  @NameDescription("시작시간_토")
  private LocalTime openTm6;

  @NameDescription("시작시간_일")
  private LocalTime openTm7;

  @NameDescription("종료시간_월")
  private LocalTime closeTm1;

  @NameDescription("종료시간_화")
  private LocalTime closeTm2;

  @NameDescription("종료시간_수")
  private LocalTime closeTm3;

  @NameDescription("종료시간_목")
  private LocalTime closeTm4;

  @NameDescription("종료시간_금")
  private LocalTime closeTm5;

  @NameDescription("종료시간_토")
  private LocalTime closeTm6;

  @NameDescription("종료시간_일")
  private LocalTime closeTm7;

}
