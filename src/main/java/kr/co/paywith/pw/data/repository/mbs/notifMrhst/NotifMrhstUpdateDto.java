package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class NotifMrhstUpdateDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 매장
	 */
	private Mrhst mrhst;

	/**
	 * 푸시 메시지
	 */
	private Notif notif;

	/**
	 * 푸시 전송 이력 일련번호
	 */
	private Integer notifHistSn;

	/**
	 * 전송 여부
	 */
	private Boolean sendFl;

	/**
	 * 전송 일시
	 */
	private ZonedDateTime sendDttm;
}