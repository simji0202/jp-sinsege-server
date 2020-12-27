package kr.co.paywith.pw.data.repository.mbs.refund;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.payment.Payment;
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
public class Refund { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 결제
	 */
	@OneToOne
	private Payment payment;

	/**
	 * 환불 금액
	 */
	private Integer refundAmt = 0;

	/**
	 * 무통장입금 했다면 환불받을 은행코드
	 */
	private String bankCode;

	/**
	 * 무통장입금 했다면 환불받을 계좌번호
	 */
	private String accountNum;

	/**
	 * 무통장입금 했다면 환불받을 예금주(기록용)
	 */
	private String acccountName;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;

	/**
	 * 수정 일시. 사용할 일 없을 듯.
	 */
	@UpdateTimestamp
	private ZonedDateTime updtDttm;

	@NameDescription("갱신담당자")
	private String updateBy;

	@NameDescription("등록담당자")
	private String createBy;



}