package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class PrpayIssuUpdateDto {
	
	/**
	 * 선불카드 발급 이름
	 */
	private String prpayIssuNm;

	/**
	 * 선불카드 발급 장수
	 */
	private Integer cnt;

	/**
	 * 등록 일시
	 */
	private ZonedDateTime regDttm;

	/**
	 * 선불카드 종류
	 */
	private PrpayGoods prpayGoods;
	/**
	 * 선불카드 종류 일련번호
	 */
	private Integer prpayGoodsSn;

	/**
	 * 발급 선불카드 목록
	 */
	private List<Prpay> prpay;

}