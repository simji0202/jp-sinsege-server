package kr.co.paywith.pw.data.repository.od.cpnMst;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.CpnTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.od.optEtc.OptEtc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class CpnMstUpdateDto {

	@NameDescription("식별번호")
	private Integer id;

	private String regUserId;

	private String cpnNm;

	private Integer cpnMasterSn;

	private Integer cpnAmt;

	private Float cpnRatio;

	private Mrhst mrhst;

	private OptEtc optEtc;

	private Integer brandSn;
	/**
	 * 주문 서비스에서 사용 여부
	 */
	private Boolean useFl = false;

	private CpnTypeCd type;

	private List<String> gooodsCdList = new ArrayList<>();

	private Integer minUseStdAmt;
}