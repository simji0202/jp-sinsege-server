package kr.co.paywith.pw.data.repository.mbs.scoreRule;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.PointCutType;
import kr.co.paywith.pw.data.repository.enumeration.ScoreRuleType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

// kms: TODO scoreRule 필요성 확인
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class ScoreRule {

	/**
	 * 점수 획득 규칙 일련번호
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private Boolean activeFl;

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
