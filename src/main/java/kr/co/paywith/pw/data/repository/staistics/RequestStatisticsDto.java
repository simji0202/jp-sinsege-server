package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class RequestStatisticsDto {

  private String date;

  @NameDescription("중복접수를 제외한 이사접수건수 ")
  private int totalCount;

  @NameDescription("업체견적완료건수 ")
  private int estimatedCount;

  @NameDescription("업체선정완료건수 ")
  private int requestCount;

  @NameDescription("견적입력건수 ")
  private int historyEstimatedCount;

  @NameDescription("상담요총건수  ")
  private int historyRequestsCount;

  @NameDescription("견적포인트")
  private int estimatePoint;

  @NameDescription("상담포인트")
  private int consultingPoint;

  @NameDescription("복구 포인트")
  private int recoveryPoint;


}
