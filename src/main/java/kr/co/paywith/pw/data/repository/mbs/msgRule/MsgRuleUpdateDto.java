package kr.co.paywith.pw.data.repository.mbs.msgRule;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.MsgRuleCd;
import kr.co.paywith.pw.data.repository.mbs.msgTemplate.MsgTemplate;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

@Data
public class MsgRuleUpdateDto {

    @NameDescription("식별번호")
    private Integer id;
	/**
	 * 생성 후 발송까지 지연시간(분)
	 */
	private Integer delayMinute;

	/**
	 * 사용 여부
	 */
	private Boolean activeFl;

	/**
	 * 메시지 규칙 종류 코드
	 */
	@Enumerated(EnumType.STRING)
	private MsgRuleCd msgRuleCd;

	/**
	 * 메시지 양식
	 */
	private MsgTemplate msgTemplate;


	/**
	 * 메시지 양식 외부 서비스 연동 아이디
	 */
	private String msgTemplateExtId;

	/**
	 * 메시지 변수 JSON
	 */
	private String msgJsonValue;

}