package kr.co.paywith.pw.data.repository.od.ordrHistReviewImg;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.od.ordrHistReview.OrdrHistReview;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

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
public class OrdrHistReviewImg { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Integer idx = 0;

	private String imgUrl;

	@ManyToOne
	private OrdrHistReview ordrHistReview;

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