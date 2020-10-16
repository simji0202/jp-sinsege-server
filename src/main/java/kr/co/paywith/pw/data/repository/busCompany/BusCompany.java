package kr.co.paywith.pw.data.repository.busCompany;

import java.time.LocalDateTime;
import javax.persistence.Id;

import kr.co.paywith.pw.common.NameDescription;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)

public class BusCompany { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	// 공통 부분
	@LastModifiedDate
	@NameDescription("갱신일")
	private LocalDateTime updateDate;

	@NameDescription("갱신담당자")
	private String updateBy;

	@CreatedDate
	@NameDescription("등록일")
	private LocalDateTime createDate;

	@NameDescription("등록담당자")
	private String createBy;

	@NameDescription("변경내용")
	private String updateContent;

}