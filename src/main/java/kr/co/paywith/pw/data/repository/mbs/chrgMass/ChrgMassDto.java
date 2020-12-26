package kr.co.paywith.pw.data.repository.mbs.chrgMass;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
public class ChrgMassDto {

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
	 * 관리자
	 */
	//  che  객체 연계를 삭제하고 CurrentUser 로 부터 아이디값을 스트링으로 저정
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Admin admin;
//	private String adminId;

	/**
	 * 매장
	 */
	private Mrhst mrhst;


}