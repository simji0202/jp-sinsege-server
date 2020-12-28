package kr.co.paywith.pw.data.repository.mbs.pointHist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointHistCd;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.pointRule.PointRule;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PointHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

	/**
	 * 포인트 양
	 */
	private Integer pointAmt;

	/**
	 * 포인트 이력 구분 코드
	 */
	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private PointHistCd pointHistCd;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;

	/**
	 * 포인트 적립 규칙
	 */
	// kms: JsonIgnore 했던 중요한 이유가 없었음(효과 : 네트워크 부하 감소)
//	@JsonIgnore
	@ManyToOne
	private PointRule pointRule;
	/**
	 * 포인트 적립 규칙 일련번호
	 */
	private Integer pointRuleSn;

	/**
	 * 취소 일시
	 */
	private ZonedDateTime cancelRegDttm;

	/**
	 * 회원
	 */
	@ManyToOne
	private UserInfo userInfo;



	// 포인트 → 충전 및 충전 → 포인트
	/**
	 * 관계있는 충전 이력
	 *
	 * 포인트로 충전한 경우, 충전하여 포인트 적립되는 경우 모두 사용
	 */
	@OneToOne
	private Chrg chrg;


	// 사용금액 비율 -> 포인트
	/**
	 * 관계있는 사용 이력
	 */
	// kms: UserInfo 가 아닌 Use
	@OneToOne
	private Use use;

	// che  UserInfo 가 중복 사용중 확인 필요
//	@OneToOne
//	private UserInfo userInfo;


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
