package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class SalesStatisticsDto {

  @NameDescription("이사 업체 아이디")
  private String id;

  @NameDescription("업체 현재 포인트 ")
  private int point;

  @NameDescription("슬로건")
  private String slogan;

  @NameDescription("팀수")
  private int teamCount;

  @NameDescription("업체명 ")
  private String name;

  @NameDescription("업체 로고 URL ")
  private String logoImgUrl;

  @NameDescription("경력 ")
  private int careerStartDate;

  @NameDescription("이사보험가입유무")
  private int hasInsurance;

  @NameDescription("결제정보")
  private String partnersPayments;

  @NameDescription("견적요청 건수 ")
  private int estimatedCount;

  @NameDescription("견정완료 건수")
  private int estimatedCompleteCount;

  @NameDescription("상담요청 건수")
  private int requestCount;

  @NameDescription("상담완료 건수 ")
  private int requestCompleteCount;

  @NameDescription("복구완료 건수")
  private int recoveryCompleteCount;

  @NameDescription("추천 Y 갯수 ")
  private int recommendCount;

  @NameDescription("추천 N 갯수")
  private int recommendNoCount;
}
