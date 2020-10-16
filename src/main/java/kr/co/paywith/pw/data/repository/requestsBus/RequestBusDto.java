package kr.co.paywith.pw.data.repository.requestsBus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.data.repository.requests.Requests;
import kr.co.paywith.pw.data.repository.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import kr.co.paywith.pw.common.NameDescription;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestBusDto {

	private Requests requests;

	@OneToOne
	@NameDescription("고객ID")
	private Schedule schedule;

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

	@NameDescription("버스타입(45인승이상,49인승)")
	@Enumerated(EnumType.STRING)
	private BusTypeEnum  busTypeCd;

	@NameDescription("버스상태(확정,미정)")
	@Enumerated(EnumType.STRING)
	private BusStateEnum  busStateCd;

}