package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelngOrdrSeatTimetableDto {

	@NameDescription("일련번호")
	private Integer id;

	@NameDescription("시작시각")
	private LocalDateTime  startDttm;

	@NameDescription("종료시각")
	private LocalDateTime  endDttm;

	@NameDescription("직원_일련번호")
	private int  staffId;

	@NameDescription("좌석_일련번호")
	private int  seatId;


}
