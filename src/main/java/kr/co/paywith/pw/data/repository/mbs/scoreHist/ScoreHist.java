package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.payment.Payment;
import kr.co.paywith.pw.data.repository.mbs.scoreRule.ScoreRule;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;

/**
 * 점수 이력
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class ScoreHist { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 점수
	 */
	private Integer scoreAmt;

	/**
	 * 회원
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private UserInfo userInfo;

	/**
	 * 취소 일시
	 */
	private ZonedDateTime cancelRegDttm;

	/**
	 * 점수 획득 규칙
	 */
	@ManyToOne
	private ScoreRule scoreRule;
	/**
	 * 점수 획득 규칙 일련번호
	 */
	private Integer scoreRuleSn;

	/**
	 * 충전 이력
	 */
	@OneToOne
	private Chrg chrg;

	/**
	 * 결제 이력
	 */
	@OneToOne
	private Payment payment;

	/**
	 * 결제 이력 일련번호
	 */
	private Long paymentSn;

	/**
	 * 사용 이력
	 */
	@OneToOne
	private Use use;


	/**
	 * 스탬프 이력
	 */
	@OneToOne
	private StampHist stampHist;

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