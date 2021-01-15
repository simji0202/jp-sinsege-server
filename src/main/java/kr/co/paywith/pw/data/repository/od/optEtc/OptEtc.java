package kr.co.paywith.pw.data.repository.od.optEtc;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.goodsOrg.GoodsOrg;
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
public class OptEtc { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	@Size(max = 50)
	private String optEtcNm;

	private Integer optEtcAmt;

	@Deprecated
	private Integer maxCnt;

	@Deprecated
	private Integer minCnt;

	@Deprecated
	private Integer defaultCnt;

	@ManyToOne
	private Brand brand;

	private Boolean useFl;

	@ManyToOne
	private GoodsOrg goodsOrg;

	@CreationTimestamp
	private LocalDateTime regDttm;

	@UpdateTimestamp
	private LocalDateTime updtDttm;


}