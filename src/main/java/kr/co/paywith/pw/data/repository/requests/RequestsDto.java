package kr.co.paywith.pw.data.repository.requests;


import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.agents.Agents;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.partners.PartnersMoveServiceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsDto {

  private Integer id;

  private Partners partners;

  @NameDescription("예약번호")
  private String reqNo;

  @NameDescription("예약상태코드(확정대기,예약확정,출발완료,예약취소)")
  private RequestStatusEnum reqStateCd;

  @NameDescription("출발일자")
  private String departDate;

  @NameDescription("출발지역")
  private String departAreaCd;

  @NameDescription("총 버스")
  private int totBus;

  @NameDescription("45인승 지정버스")
  private int seaterBus45;

  @NameDescription("49인승 지정버스")
  private int seaterBus49;

  @NameDescription("선박이름")
  private String cruiseShipName;

  @NameDescription("중국 여행사명")
  private String travalAgencyName;

  @NameDescription("고객 접수단말기(PC, Mobile, Call)")
  private String clientDevice;

  @NameDescription("비고")
  private String comment;


}
