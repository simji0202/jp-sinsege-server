package kr.co.paywith.pw.data.repository.requests;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.partners.PartnersMoveServiceEnum;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestsList {

  private Integer id;

  @NameDescription("파트너사 명")
  private String partnersName;

  @NameDescription("예약번호")
  private String  reqNo;

  @NameDescription("예약상태코드(확정대기,예약확정,출발완료,예약취소)")
  private RequestStatusEnum  reqStateCd;

  @NameDescription("출발일자")
  private String departDate;

  @NameDescription("출발지역")
  private String  departAreaCd;

  @NameDescription("총 버스")
  private int  totBus;

  @NameDescription("49인승 지정버스")
  private int seaterBus49;

  @NameDescription("총 버스 확정  ")
  private int totBusDecide;

  @NameDescription("49인승 지정버스 확정 ")
  private int seaterBusDecide;

  @NameDescription("선박이름")
  private String  cruiseShipName;

  @NameDescription("중국 여행사명")
  private String  travalAgencyName;

  @NameDescription("등록일")
  private LocalDateTime createDate;


}
