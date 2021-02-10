//package kr.co.paywith.pw.data.repository.mbs.notifMrhst;
//
//import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
//import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
//import kr.co.paywith.pw.common.NameDescription;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.ManyToOne;
//import java.time.LocalDateTime;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class NotifMrhstDto {
//
//	@NameDescription("식별번호")
//	private Integer id;
//
//	/**
//	 * 매장
//	 */
//	private Mrhst mrhst;
//
//	/**
//	 * 푸시 메시지
//	 */
//	private Notif notif;
//
//	/**
//	 * 푸시 전송 이력 일련번호
//	 */
//	private Integer notifHistSn;
//
//	/**
//	 * 전송 여부
//	 */
//	private Boolean sendFl;
//
//	/**
//	 * 전송 일시
//	 */
//	private LocalDateTime sendDttm;
//
//
//}
