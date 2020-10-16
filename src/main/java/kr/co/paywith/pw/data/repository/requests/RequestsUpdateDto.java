package kr.co.paywith.pw.data.repository.requests;


import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.requestsBus.RequestBus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsUpdateDto {

  private Integer id;

  @NameDescription("예약상태코드(확정대기,예약확정,출발완료,예약취소)")
  private RequestStatusEnum reqStateCd;

  @NameDescription("총 버스")
  private int totBus;

  @NameDescription("49인승 지정버스")
  private int seaterBus49;

  @NameDescription("선박이름")
  private String cruiseShipName;

  @NameDescription("중국 여행사명")
  private String travalAgencyName;

  private List<RequestBus> requestBuses = new ArrayList<RequestBus>();

  @NameDescription("비고")
  private String comment;


}
