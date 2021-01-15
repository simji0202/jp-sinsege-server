package kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.od.goodsOptEtc.GoodsOptEtc;
import kr.co.paywith.pw.data.repository.od.ordrGoodsOpt.OrdrGoodsOpt;
import kr.co.paywith.pw.data.repository.od.ordrOptEtcCpn.OrdrOptEtcCpn;
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
public class OrdrGoodsOptEtc { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Integer goodsOptEtcAmt;

	private Integer goodsOptEtcCnt;

	@ManyToOne
	private OrdrGoodsOpt ordrGoodsOpt;

	@ManyToOne
	private GoodsOptEtc goodsOptEtc;

	@OneToOne
	private OrdrOptEtcCpn ordrOptEtcCpn;


}