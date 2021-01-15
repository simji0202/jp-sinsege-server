package kr.co.paywith.pw.data.repository.od.dcMst;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DcMstDto {

	@NameDescription("식별번호")
	private Integer id;

	private Integer dcAmt;

	private Float dcRatio;

	private String dcCn;

	private String regUserId;

	private Brand brand;

	private Mrhst mrhst;

}