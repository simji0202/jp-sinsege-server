package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.PrpayHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
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
public class PrpayHist { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Long prpayHisnSn;

	private ZonedDateTime histDttm = ZonedDateTime.now();

	/**
	 * 충전 이력
	 */
	@ManyToOne
	private Chrg chrg;
	/**
	 * 사용 이력
	 */
	@ManyToOne
	private Use use;


	@ManyToOne
	private Prpay prpay;


	@Enumerated(EnumType.STRING)
	private PrpayHistTypeCd histTypeCd;
}