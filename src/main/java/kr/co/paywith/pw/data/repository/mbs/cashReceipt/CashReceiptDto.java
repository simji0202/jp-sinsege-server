package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * 현금영수증 발급 한 사용이력
	 */
	@ManyToOne
	private Use use;

	/**
	 * 사용이력 일련번호
	 */
	@Column
	private Long useSn;

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