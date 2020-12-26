package kr.co.paywith.pw.data.repository.mbs.pointUseRule;

import javax.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PointUseRule { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

//	/**
//	 * 브랜드
//	 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "brandSn", insertable = false, updatable = false)
//	private Brand brand;
//	/**
//	 * 브랜드 일련번호
//	 */
//	private Integer brandSn;
//
//	/**
//	 * 회원 등급
//	 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "gradeSn", insertable = false, updatable = false)
//	private Grade grade;
//	/**
//	 * 회원등급 일련번호
//	 */
//	private Integer gradeSn;

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

	/**
	 * 추가한 관리자
	 * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
	 */
	private String createBy;

	/**
	 * 변경한  관리자
	 * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
	 */
	private String updateBy;


}