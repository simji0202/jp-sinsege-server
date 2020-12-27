package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
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
public class NotifMrhst { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 매장
	 */
	@ManyToOne
 	private Mrhst mrhst;

	/**
	 * 푸시 메시지
	 */
	@ManyToOne
	private Notif notif;

	/**
	 * 푸시 전송 이력 일련번호
	 */
	private Integer notifHistId;

	/**
	 * 전송 여부
	 */
	private Boolean sendFl;

	/**
	 * 전송 일시
	 */
	private ZonedDateTime sendDttm;
}