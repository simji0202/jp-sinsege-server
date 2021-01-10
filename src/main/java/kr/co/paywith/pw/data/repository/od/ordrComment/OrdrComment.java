package kr.co.paywith.pw.data.repository.od.ordrComment;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.OrdrStatus;
import kr.co.paywith.pw.data.repository.od.ordrCommentImg.OrdrCommentImg;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import kr.co.paywith.pw.data.repository.od.statProc.StatProc;
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
public class OrdrComment { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 작성 당시의 주문 상태
	 */
	@Enumerated(EnumType.STRING)
	private OrdrStatus ordrStatCd;

	/**
	 * 제목
	 */
	@Column(name = "sj")
	private String sj;

	/**
	 * 본문
	 */
	@Lob
	private String cn;

	@Size(max = 30)
	@Column(name = "ordr_user_id", length = 30)
	private String userId;

	@OneToMany
	private List<OrdrCommentImg> ordrCommentImgList;

	/**
	 * 삭제(표시X) 여부
	 */
	private Boolean delFl = false;

	@ManyToOne
	private OrdrHist ordrHist;

	@ManyToOne
	private StatProc statProc;

	@CreationTimestamp
	private LocalDateTime regDttm;

	@UpdateTimestamp
	private LocalDateTime updtDttm;

	/**
	 * 추가한 관리자
	 */
	private String createBy;

	/**
	 * 변경한  관리자
	 */
	private String updateBy;


}