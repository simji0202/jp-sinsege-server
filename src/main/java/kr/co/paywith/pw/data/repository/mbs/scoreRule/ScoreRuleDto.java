package kr.co.paywith.pw.data.repository.mbs.scoreRule;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointCutType;
import kr.co.paywith.pw.data.repository.enumeration.ScoreRuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRuleDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 점수 획득 규칙 구분 코드
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private ScoreRuleType scoreRuleType;
	/**
	 * 소수점 처리 규칙 코드
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private PointCutType pointCutType;
	/**
	 * 전환 비율
	 */
	private Float ratioValue;

	/**
	 * 사용 여부
	 */
	private Boolean activeFl= false;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private LocalDateTime regDttm;

	/**
	 * 수정 일시
	 */
	@UpdateTimestamp
	private LocalDateTime updtDttm;

	@NameDescription("갱신담당자")
	private String updateBy;

	@NameDescription("등록담당자")
	private String createBy;
}
