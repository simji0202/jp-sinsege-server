package kr.co.paywith.pw.data.repository.busCompany;


import kr.co.paywith.pw.common.NameDescription;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusCompanyDto {

	@NameDescription("버스회사_일련번호")
	private Integer id;

	@NameDescription("버스회사명")
	private String  busCompanyName;

	@NameDescription("버스회사 연락처 국가코드")
	private String  busTelCrCd;

	@NameDescription("버스회사 연락처")
	private String  busTel;

	@NameDescription("버스회사 팩스 국가코드")
	private String  busFaxCrCd;

	@NameDescription("버스회사 팩스")
	private String  busFax;

	@NameDescription("담당자명")
	private String  repName;

	@NameDescription("비고")
	private String  remarks;

	@NameDescription("갱신일")
	private LocalDateTime  updateDate;

	@NameDescription("갱신담당자")
	private String  updateBy;

	@NameDescription("등록일")
	private LocalDateTime  createDate;

	@NameDescription("등록담당자")
	private String  createBy;


}