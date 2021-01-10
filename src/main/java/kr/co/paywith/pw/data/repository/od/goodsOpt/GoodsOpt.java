package kr.co.paywith.pw.data.repository.od.goodsOpt;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.cate.Cate;
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
public class GoodsOpt { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	@Size(max = 100)
	private String goodsOptNm;

	private Integer goodsOptAmt;

	@Size(max = 200)
	private String goodsOptImgUrl;

	private boolean stockFl;

	private boolean newFl;

	private boolean bestFl;

	private boolean recmdFl;

	private boolean dpFl = true;

	private Integer sort;

	private Integer maxCnt;

	@Lob
	private String goodsOptCn;

	private Integer rsrvUseTime;

	@ManyToOne
	private Brand brand;

	@ManyToOne
	private GoodsOrg goodsOrg;

	@ManyToOne
	private Cate cate;

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



}