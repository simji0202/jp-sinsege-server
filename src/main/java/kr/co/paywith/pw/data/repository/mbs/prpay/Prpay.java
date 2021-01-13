package kr.co.paywith.pw.data.repository.mbs.prpay;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.paywith.pw.data.repository.enumeration.PrpaySttsCd;
import kr.co.paywith.pw.data.repository.mbs.billing.Billing;
import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
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
public class Prpay {

	/**
	 * 선불카드 일련번호
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 선불카드 번호
	 */
	private String prpayNo;

	/**
	 * 선불카드 이름
	 */
	private String prpayNm;

	/**
	 * PIN 번호
	 */
	private String pinNum;


	/**
	 * 최초 사용 일시
	 */
	private ZonedDateTime useDttm;

	/**
	 * 유효 일시
	 */
	private ZonedDateTime validDttm;

	/**
	 * 선불카드 상태 코드
	 */
	@Enumerated(EnumType.STRING)
	private PrpaySttsCd prpaySttsCd = PrpaySttsCd.AVAIL;

	/**
	 * 사용 가능 금액
	 */
	private Integer usePosblAmt = 0;

	/**
	 * 최대 충전 가능 금액
	 */
	private Integer chrgMaxAmt = 99999999;

	/**
	 * 총 충전 금액
	 */
	private Integer chrgTotAmt = 0;
	/**
	 * 총 사용 금액
	 */
	private Integer useTotAmt = 0;

	/**
	 * 회원
	 */
	@ManyToOne
	private UserInfo userInfo;

	/**
	 * 정렬순서
	 */
	private Integer sort;

	/**
	 * 선불카드 종류
	 */
	@OneToOne
	private PrpayGoods prpayGoods;

  /**
   * 정지 일시
   */
  private ZonedDateTime stopDttm;

  /**
   * 유저에게 카드 발급 일시
   */
  private ZonedDateTime registDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private ZonedDateTime updtDttm;


  /**
   * 생성 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;


}