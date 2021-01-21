package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.MsgType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
	private MsgType msgType;

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