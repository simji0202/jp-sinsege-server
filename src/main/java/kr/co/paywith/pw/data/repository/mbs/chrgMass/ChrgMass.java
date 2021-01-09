package kr.co.paywith.pw.data.repository.mbs.chrgMass;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

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
public class ChrgMass { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;
	/**
	 * 충전금액
	 */
	private Integer chrgAmt;
	/**
	 * 포인트로 충전 여부(포인트 일때는 chrg 내에 chrgAmt만 가산, setleAmt는 0
	 */
	private Boolean pointFl = false;
	/**
	 * 충전한 선불카드 매수(=건수)
	 */
	private Integer chrgCnt = 0;
	/**
	 * 총 충전한 금액
	 */
	private Integer chrgTotAmt = 0;
	/**
	 * 매장
	 */
	@ManyToOne
	private Mrhst mrhst;
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