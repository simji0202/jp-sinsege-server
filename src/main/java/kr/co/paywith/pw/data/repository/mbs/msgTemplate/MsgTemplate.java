package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.MsgTypeCd;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class MsgTemplate { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 메시지 양식 이름
	 */
	private String msgTemplateNm;

	/**
	 * 사용 여부
	 */
	private Boolean activeFl;
	/**
	 * 광고 메시지 여부
	 */
	private Boolean adsFl;

	/**
	 * 메시지 종류 코드
	 */
	@Enumerated(EnumType.STRING)
	private MsgTypeCd msgTypeCd;

	/**
	 * 메시지 제목
	 */
	private String msgSj;

	/**
	 * 메시지 본문
	 */
	@Lob
	private String msgCn;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;
	/**
	 * 수정 일시
	 */
	@UpdateTimestamp
	private ZonedDateTime updtDttm;

	@NameDescription("갱신담당자")
	private String updateBy;

	@NameDescription("등록담당자")
	private String createBy;

}

