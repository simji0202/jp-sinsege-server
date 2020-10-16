package kr.co.paywith.pw.data.repository.requests;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.requestsBus.RequestBus;
import  lombok.*;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
/**
 *  여행사 버스 수배 신청
 */
public class Requests {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("접수번호")
  private Integer id;

  @OneToOne
  private Partners partners;

  @OneToMany(mappedBy = "requests", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<RequestBus> requestBuses = new ArrayList<RequestBus>();

  @NameDescription("예약번호")
  private String  reqNo;

  @NameDescription("예약상태코드(확정대기,예약확정,출발완료,예약취소)")
  @Enumerated(EnumType.STRING)
  private RequestStatusEnum  reqStateCd;

  @NameDescription("출발일자")
  private String  departDate;

  @NameDescription("출발지역")
  private String  departAreaCd;

  @NameDescription("총 버스")
  private int  totBus;

  @NameDescription("49인승 지정버스")
  private int seaterBus49;

  @NameDescription("총 버스 확정  ")
  private int  totBusDecide;

  @NameDescription("49인승 지정버스 확정 ")
  private int seaterBusDecide;

  @NameDescription("선박이름")
  private String  cruiseShipName;

  @NameDescription("중국 여행사명")
  private String  travalAgencyName;

  @NameDescription("고객 접수단말기(PC, Mobile, Call)")
  private String clientDevice;

  @NameDescription("비고")
  private String comment;

  // 공통 부분
  @LastModifiedDate
  @NameDescription("갱신일")
  private LocalDateTime updateDate;

  @NameDescription("갱신담당자")
  private String updateBy;

  @CreatedDate
  @NameDescription("등록일")
  private LocalDateTime createDate;

  @NameDescription("등록담당자")
  private String createBy;

  @NameDescription("변경내용")
  @Lob
  private String updateContent;


}
