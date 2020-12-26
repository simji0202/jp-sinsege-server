package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.MsgTypeCd;
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
public class MsgTemplateDto {

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

}