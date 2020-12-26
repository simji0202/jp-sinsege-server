package kr.co.paywith.pw.data.repository.mbs.pointUseRule;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
@Data
public class PointUseRuleUpdateDto {

	/**
	 * 선불카드에 적립 될 시간
	 */
	private Integer changeHour; // 선불카드 적립될 시간

	/**
	 * 사용 가능 최소 기준 금액
	 */
	private Integer minAmt; // 사용 최저 제한

	/**
	 * 사용 여부
	 */
	private Boolean activeFl;

}