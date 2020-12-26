package kr.co.paywith.pw.data.repository.mbs.prpayGoods;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PrpayOperCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrpayGoodsDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 선불카드 종류 이름
	 */
	private String prpayGoodsNm;
	/**
	 * 선불카드 이미지 웹 경로
	 */
	private String prpayImg;

	/**
	 * 선불카드 제공(구분) 코드
	 */
	@Enumerated(EnumType.STRING)
	private PrpayOperCd prpayOperCd;

	/**
	 * 사용 여부
	 */
	private Boolean activeFl;

}