package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

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
public class DelngOrdrDto {

	@NameDescription("일련번호")
	private Integer id;

	@NameDescription("접수시각")
	private LocalDateTime  acceptDttm;

	@NameDescription("완료시각")
	private LocalDateTime  compDttm;

	@NameDescription("취소시각")
	private LocalDateTime  cancelDttm;

	@NameDescription("요청사항")
	private String  reqCn;

	@NameDescription("거래 주문_좌석_일련번호")
	private int  delngOrdrSeatTimetableId;

	@NameDescription("연락처")
	private String  mobileNum;


}
