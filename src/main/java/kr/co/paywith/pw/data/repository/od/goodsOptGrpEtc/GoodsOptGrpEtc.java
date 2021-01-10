package kr.co.paywith.pw.data.repository.od.goodsOptGrpEtc;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.od.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.od.goodsOptEtc.GoodsOptEtc;
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
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class GoodsOptGrpEtc { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Integer minCnt;

	private Integer maxCnt;

	private Integer sort = 0;

	@Size(max = 30)
	private String optEtcGrpNm;

	@ManyToOne
	private GoodsOpt goodsOpt;

	@OneToMany
	private List<GoodsOptEtc> goodsOptEtcList;

	private Boolean delFl;

	@CreationTimestamp
	private LocalDateTime regDttm;

	@UpdateTimestamp
	private LocalDateTime updtDttm;


}