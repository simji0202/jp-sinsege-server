package kr.co.paywith.pw.data.repository.od.ordrHistReview;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import kr.co.paywith.pw.data.repository.od.ordrHistReviewImg.OrdrHistReviewImg;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

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
public class OrdrHistReview { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 제목
	 */
	private String sj;
	/**
	 * 본문
	 */
	@Lob
	private String cn;

	@OneToMany
	private List<OrdrHistReviewImg> ordrHistReviewImgList;

	@ManyToOne
	private OrdrHist ordrHist;

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