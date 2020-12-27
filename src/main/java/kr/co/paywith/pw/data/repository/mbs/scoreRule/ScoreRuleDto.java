package kr.co.paywith.pw.data.repository.mbs.scoreRule;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointCutTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.ScoreRuleTypeCd;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private ScoreRuleTypeCd scoreRuleTypeCd;
	/**
	 * 소수점 처리 규칙 코드
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private PointCutTypeCd pointCutTypeCd;
	/**
	 * 전환 비율
	 */
	private Float ratioValue;

	/**
	 * 사용 여부
	 */
	private Boolean activeFl;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;

	/**
	 * 수정 일시
	 */
	@UpdateTimestamp
	private ZonedDateTime updtDttm;

	@NameDescription("갱신담당자")
	private String updateBy;

	@NameDescription("등록담당자")
	private String createBy;
}