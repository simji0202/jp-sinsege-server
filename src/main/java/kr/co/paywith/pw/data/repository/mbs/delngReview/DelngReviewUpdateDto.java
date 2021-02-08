package kr.co.paywith.pw.data.repository.mbs.delngReview;

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
public class DelngReviewUpdateDto {

	@NameDescription("일련번호")
	private Integer id;

	@NameDescription("제목")
	private String  sj;

	@NameDescription("내용")
	private String  cn;

	@NameDescription("점수")
	private int  score;

	@NameDescription("매장_주문_일련번호")
	private int  mrhstOrdrId;

	@NameDescription("거래_주문_일련번호")
	private int  delngOrdrId;


}
