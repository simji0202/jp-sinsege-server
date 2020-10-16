package kr.co.paywith.pw.data.repository.schedule;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.partners.Partners;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

	private Integer id;

	@NameDescription("일정이름")
	private String  scheduleName;

	@NameDescription("파트너사")
	@OneToOne
	private Partners partners;


	@NameDescription("일정정보Json")
	@Column(columnDefinition = "json")
	private String  scheduleInfoJson;

}