package kr.co.paywith.pw.data.repository.requestsBus;

import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.agents.Agents;
import kr.co.paywith.pw.data.repository.partners.BusGuide;
import kr.co.paywith.pw.data.repository.requests.Requests;
import kr.co.paywith.pw.data.repository.requests.RequestsSerializer;
import kr.co.paywith.pw.data.repository.schedule.Schedule;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)

public class RequestBus { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("예약_버스 일련번호")
	private Integer id;

	@ManyToOne
	@JsonSerialize(using = RequestsSerializer.class)
	private Requests requests;

	@OneToOne
	@NameDescription("스케쥴ID")
	private Schedule schedule;

	@OneToOne
	@NameDescription("버스업체 ")
	private Agents agent;

	@NameDescription("단체명")
	private String  groupName;

	@NameDescription("인원")
	private int  personnel;

	@NameDescription("버스호차")
	private int  busSeq;

	@NameDescription("버스회사명")
	private String  busCompanyName;

	@NameDescription("버스 드라이버 이름")
	private String  busDriverName;

	@NameDescription("버스 드라이버 연락처")
	private String  busDriverTel;

	@NameDescription("차량번호")
	private String  busNo;

	@NameDescription("버스 가이드정보")
	@OneToOne
	private BusGuide busGuide;

	@NameDescription("버스타입(45인승이상,49인승)")
	@Enumerated(EnumType.STRING)
	private BusTypeEnum  busTypeCd;

	@NameDescription("버스상태(확정,미정)")
	@Enumerated(EnumType.STRING)
	private BusStateEnum  busStateCd;

}
