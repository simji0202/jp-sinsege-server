package kr.co.paywith.pw.data.repository.od.cate;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Cate { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private String cateNm;

	private String cateCd;

	private Integer sort;

	@ManyToOne
	private Brand brand;


	// che2 셀프조인 ?
	// kms: 셀프조인은 맞는데, GoodsGrp 를 사용하도록 변경 예정.
	// kms: mbs.GoodsGrp -> 시스템 내부에서 관리하던 상품 분류 그룹. od.Cate -> 앱에서 주문할 때 사용자가 확인하는 카테고리.
	// kms: 둘 구조는 같고, mbs.GoodsGrp의 쓸모가 적어서 하나로 통합 예정
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "parent_id", nullable = true)
//	private List<Cate> subCateList;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "use_fl")
	private Boolean useFl;


	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;
	/**
	 * 수정 일시
	 */
	@UpdateTimestamp
	private ZonedDateTime updtDttm;

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