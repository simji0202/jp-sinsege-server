package kr.co.paywith.pw.data.repository.mbs.gift;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.MsgHistSttsCd;
import kr.co.paywith.pw.data.repository.enumeration.MsgTypeCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
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
public class Gift {

	/**
	 *
	 *
	 *  선물카드 (쿠폰)
	 *  금액 -->  추가
	 */




	/**
	 * 선물 일련번호
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;


	/**
	 * 예약발송 여부
	 */
	@Column(nullable = false)
	private Boolean reserveFl;

//	/**
//	 * 선불카드
//	 */
//	@ManyToOne
//	private Prpay prpay;

	/**
	 * 선불카드 일련번호
	 */
	private Long prpaySn;

	/**
	 * 발신 회원
	 */
	@ManyToOne
	private UserInfo sendUser;


	/**
	 * 발송 일시
	 */
	private ZonedDateTime sendDttm;

	/**
	 * 선물 내용
	 */
	private String sendCn;

	/**
	 * 선물 메시지 구분 코드
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MsgTypeCd msgTypeCd;

	/**
	 * 선물 상태 코드
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MsgHistSttsCd msgHistSttsCd;


	/**
	 * 수신 회원
	 */
	@ManyToOne
	private UserInfo rcvUser;

	/**
	 * 수신 회원 이름
	 */
	private String rcvUserNm;

	/**
	 * 수신 전화번호
	 */
	@Column(nullable = false)
	private String rcvMobileNum;

	/**
	 * 결제 금액
	 */
	@Column(nullable = false)
	private Integer setleAmt;

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


}