package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class FrontStatisticsDto {

  @NameDescription("전체 접수 건수")
  private int todayRequestsCount;

  @NameDescription("전체 접수 건수")
  private int requestsCount;

  @NameDescription("이사 업체 건수")
  private int partnersCount;

  @NameDescription("리뷰 건수")
  private int requestReviewsCount;

}
