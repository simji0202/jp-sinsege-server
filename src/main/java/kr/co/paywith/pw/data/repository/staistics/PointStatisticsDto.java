package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class PointStatisticsDto {

  private String date;

  @NameDescription("전체 접수 건수")
  private int requestCount;

  @NameDescription("접수 완료 건수")
  private int requestCompleteCount;

  @NameDescription("평균 견적 건수")
  private int estimateCount;

  @NameDescription("평균 상담 건수")
  private int consultingCount;

  @NameDescription("모든 포인트 이력")
  private int point;

  @NameDescription("견적 포인트")
  private int estimatePoint;

  @NameDescription("상담 포인트")
  private int consultingPoint;

  @NameDescription("입금 포인트")
  private int depositPoint;

  @NameDescription("복구 포인트")
  private int recoveryPoint;

  @NameDescription("보너스포인트")
  private int bonusPoint;

  @NameDescription("출금(환불)포인트")
  private int refundPoint;

}
