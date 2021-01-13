package kr.co.paywith.pw.data.repository.od.goodsOptEtc;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.od.goodsOptGrpEtc.GoodsOptGrpEtc;
import kr.co.paywith.pw.data.repository.od.optEtc.OptEtc;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
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
public class GoodsOptEtc implements  Comparable<GoodsOptEtc> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Integer goodsOptEtcAmt;

	private Integer defaultCnt;

	@Size(max = 30)
	private String goodsCd;

	private Integer sort = 0;

	private Integer maxCnt;

	private Integer minCnt;

	@ManyToOne
	private Brand brand;

	@ManyToOne
	private OptEtc optEtc;

	@ManyToOne
	private GoodsOptGrpEtc goodsOptGrpEtc;

	@ManyToOne
	private GoodsOpt goodsOpt;

	private Boolean delFl = false;

	@CreationTimestamp
	private LocalDateTime regDttm;

	@UpdateTimestamp
	private LocalDateTime updtDttm;

	/**
	 * 추가한 관리자
	 * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
	 */
	private String createBy;

	/**
	 * 변경한  관리자
	 * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
	 */
	private String updateBy;



	@Override
	public int compareTo(GoodsOptEtc o) {
		return this.getSort() - o.getSort();
	}

	public Integer getSort() {
		return this.sort == null ? 0 : this.sort;
	}

}