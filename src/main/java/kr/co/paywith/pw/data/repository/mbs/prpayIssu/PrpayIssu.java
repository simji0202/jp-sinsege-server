package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
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
public class PrpayIssu {



	/**
	 * 선불카드 발급 이력 일련번호
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;



	/**
	 * 선불카드 발급 이름
	 */
	@Column(length = 30)
	private String prpayIssuNm;

	/**
	 * 선불카드 발급 장수
	 */
	private Integer cnt;
//  /**
//   * 선불카드 제공(구분) 코드
//   */
//  @Column(length = 10)
//  @Enumerated(EnumType.STRING)
//  private PrpayOperCd prpayOperCd;

	/**
	 * 등록 일시
	 */
	@CreationTimestamp
	private ZonedDateTime regDttm;

	/**
	 * 선불카드 종류
	 */
	@ManyToOne
	private PrpayGoods prpayGoods;
	/**
	 * 선불카드 종류 일련번호
	 */
	private Integer prpayGoodsSn;



	/**
	 * 발급 선불카드 목록
	 */
	@OneToMany(mappedBy = "prpayIssu", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Prpay> prpay;

}