package kr.co.paywith.pw.data.repository.mbs.gift;

import kr.co.paywith.pw.data.repository.enumeration.MsgHistSttsType;
import kr.co.paywith.pw.data.repository.enumeration.MsgType;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
public class GiftUpdateDto {

	/**
	 * 예약발송 여부
	 */
	private Boolean reserveFl;

	/**
	 * 선불카드
	 */
	// private Prpay prpay;
	/**
	 * 선불카드 일련번호
	 */
	private Long prpaySn;

	/**
	 * 발신 회원
	 */
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
	@Enumerated(EnumType.STRING)
	private MsgType msgType;

	/**
	 * 선물 상태 코드
	 */
	@Enumerated(EnumType.STRING)
	private MsgHistSttsType msgHistSttsType;


	/**
	 * 수신 회원
	 */
	private UserInfo rcvUser;

	/**
	 * 수신 회원 이름
	 */
	private String rcvUserNm;

	/**
	 * 수신 전화번호
	 */
	private String rcvMobileNum;

	/**
	 * 결제 금액
	 */
	private Integer setleAmt;




}