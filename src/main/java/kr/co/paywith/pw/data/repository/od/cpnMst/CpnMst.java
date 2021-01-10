package kr.co.paywith.pw.data.repository.od.cpnMst;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.CpnTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.od.optEtc.OptEtc;
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
public class CpnMst { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	@Size(max = 30)
	private String regUserId;

	private String cpnNm;

	private Integer cpnMasterSn;

	private Integer cpnAmt;

	private Float cpnRatio;

	@ManyToOne
	private Mrhst mrhst;

	@ManyToOne
	private OptEtc optEtc;

	/**
	 * 주문 서비스에서 사용 여부
	 */
	private Boolean useFl = false;

	@Enumerated(EnumType.STRING)
	private CpnTypeCd type;

	@ElementCollection
	private List<String> gooodsCdList = new ArrayList<>();

	private Integer minUseStdAmt;

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