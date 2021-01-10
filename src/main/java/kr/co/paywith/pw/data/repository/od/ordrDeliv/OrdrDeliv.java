package kr.co.paywith.pw.data.repository.od.ordrDeliv;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

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
public class OrdrDeliv { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	@Size(max = 13)
	private String recvMobile;

	@Size(max = 30)
	private String recvNm;

	@Max(value = 200)
	private Integer delivTm;

	@Size(max = 6)
	private String delivZipcode;

	@Size(max = 200)
	private String delivAddr;

	@Size(max = 200)
	private String delivAddrDtl;

	@Size(max = 1000)
	private String delivReqCn;

	private Integer delivAmt;

	private boolean prpayFl;

	@ManyToOne
	private Brand brand;

	@OneToOne
	private OrdrHist ordrHist;


}