package kr.co.paywith.pw.data.repository.mbs.questCpnRule;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestCpnRuleDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 퀘스트 쿠폰 규칙 이름
	 */
	private String questCpnRuleNm;

	/**
	 * 최대 달성 횟수
	 */
	private Integer maxCnt; // 최대 달성 횟수

}