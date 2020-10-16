package kr.co.paywith.pw.data.repository.schedule;

import java.time.LocalDateTime;
import javax.persistence.Id;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.partners.Partners;
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

public class Schedule { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("일정_일련번호")
	private Integer id;

	@NameDescription("일정이름")
	private String  scheduleName;

	@NameDescription("파트너사")
	@OneToOne
	private Partners partners;


	@NameDescription("일정정보Json")
	@Column(columnDefinition = "json")
	private String  scheduleInfoJson;

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