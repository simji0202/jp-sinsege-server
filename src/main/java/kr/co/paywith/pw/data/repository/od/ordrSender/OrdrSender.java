package kr.co.paywith.pw.data.repository.od.ordrSender;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class OrdrSender { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private String senderMobile;

	private String senderNm;

	private LocalDateTime visitStartDttm;

	private LocalDateTime visitEndDttm;

	private String senderZipcode;

	private String senderAddr;

	private String senderAddrDtl;

	private String senderReqCn;

	@ManyToOne
	private Brand brand;

	@OneToOne
	private OrdrHist ordrHist;

}