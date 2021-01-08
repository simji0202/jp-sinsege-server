package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PrpayHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
	 * 선불카드 결제 이력
	 */
	@ManyToOne
	private DelngPayment delngPayment;


	@ManyToOne
	private Prpay prpay;


	@Enumerated(EnumType.STRING)
	private PrpayHistTypeCd histTypeCd;
}