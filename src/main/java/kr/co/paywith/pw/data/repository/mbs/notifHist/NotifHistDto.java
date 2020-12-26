package kr.co.paywith.pw.data.repository.mbs.notifHist;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class NotifHistDto {

	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 매장
	 */
	private Mrhst mrhst;

	/**
	 * 매장 일련번호
	 */
	private Integer mrhstSn;

	/**
	 * 푸시 메시지
	 */
	private Notif notif;

	/**
	 * 푸시 일련번호
	 */
	private Integer notifSn;

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