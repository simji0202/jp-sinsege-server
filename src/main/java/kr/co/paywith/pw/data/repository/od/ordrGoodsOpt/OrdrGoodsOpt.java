package kr.co.paywith.pw.data.repository.od.ordrGoodsOpt;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.od.ordrGoodsCpn.OrdrGoodsCpn;
import kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc.OrdrGoodsOptEtc;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import kr.co.paywith.pw.data.repository.od.timesale.Timesale;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class OrdrGoodsOpt { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Integer goodsOptCnt;

	private Integer goodsOptAmt;

	@ManyToOne(fetch = FetchType.EAGER)
	private OrdrHist ordrHist;

	@OneToMany
	private List<OrdrGoodsOptEtc> goodsOptEtcList = new ArrayList<>();

	@ManyToOne
	private GoodsOpt goodsOpt;

	@ManyToOne
	private Brand brand;

	@ManyToOne
	private Timesale timesale;

	@OneToOne
	private OrdrGoodsCpn ordrGoodsCpn;


}