package kr.co.paywith.pw.data.repository.od.ordrCancel;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class OrdrCancel { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	@Size(max = 1000)
	private String cancelCn;

	private Boolean refundFl = false;

	@Size(max = 20)
	private String refundAccno;

	@Size(max = 20)
	private String refundBankCd;

	@CreationTimestamp
	private LocalDateTime cancelDttm;

	/**
	 * 취소 요청한 userId. 멤버십 서버에서는 userSn이지만 ordr에서 String으로 변환시켜 넣으므로 이를 넣는다.
	 */
	@Size(max = 30)
	private String cancelUserId;

	private Integer cancelMrhstTrmnlSn;

	private Integer cancelAdminSn;

	private String cancelClientIp;

	@ManyToOne(fetch = FetchType.LAZY)
	private Brand brand;

	@OneToOne
	private OrdrHist ordrHist;

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
	 */
	private String createBy;

	/**
	 * 변경한  관리자
	 */
	private String updateBy;
}