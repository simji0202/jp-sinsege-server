package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashReceiptDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 현금영수증 국세청승인번호
	 */
	private String orgConfmNo;

	/**
	 * 현금영수증 거래일자 yyyyMMdd
	 */
	@Column(length = 8)
	private String orgTradeDate;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;

	/**
	 * 취소 일시
	 */
	private ZonedDateTime cancelRegDttm;

	/**
	 * 취소 전송 예약 여부
	 */
	private Boolean cancelSendRsrvFl = false;
}