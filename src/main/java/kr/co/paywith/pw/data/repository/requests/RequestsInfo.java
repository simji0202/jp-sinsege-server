package kr.co.paywith.pw.data.repository.requests;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class RequestsInfo {

  @NameDescription("날짜")
  private String date;

  @NameDescription("총 예약수")
  private Integer totalRequestCount;

  @NameDescription("예약신청")
  private Integer requestApplyCount;

  @NameDescription("예약신청 완료 갯수")
  private Integer requestApplyCompleteCount;

  @NameDescription("예약신청변경 갯수")
  private Integer requestApplyChangedCount;

  @NameDescription("버스수배 갯수")
  private Integer busWantedCount;

  @NameDescription("버스수배완료 갯수")
  private Integer busWantedCompleteCount;

  @NameDescription("예약 확정 갯수")
  private Integer requestConfirmCount;

  @NameDescription("예약 취소 갯수")
  private Integer requestCancelCount;







}
