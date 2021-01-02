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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable = true)
	private List<Cate> subCateList;

	private Long parentId;

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