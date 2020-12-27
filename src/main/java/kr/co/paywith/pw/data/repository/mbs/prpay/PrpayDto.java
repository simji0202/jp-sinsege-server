package kr.co.paywith.pw.data.repository.mbs.prpay;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PrpaySttsCd;
import kr.co.paywith.pw.data.repository.mbs.billing.Billing;
import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import kr.co.paywith.pw.data.repository.mbs.prpayIssu.PrpayIssu;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrpayDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * 선불카드 번호
     */
    private String prpayNo;
    // prpayGoodsN
    /**
     * 선불카드 이름
     */
    private String prpayNm;

    /**
     * PIN 번호
     */
    private String pinNum;

    /**
     * 생성 일시
     */
    private ZonedDateTime regDttm;
    /**
     * 등록 일시
     */
    private ZonedDateTime registDttm; // 회원이 등록한 일시
    /**
     * 사용 일시
     */
    private ZonedDateTime useDttm;
    /**
     * 유효 일시
     */
    private ZonedDateTime validDttm;
    /**
     * 정지 일시
     */
    private ZonedDateTime stopDttm;

    /**
     * 수정 일시
     */
    private ZonedDateTime updtDttm;
//  /**
//   * 선불카드 제공(구분) 코드
//   */
//  @Column(length = 10)
//  @Enumerated(EnumType.STRING)
//  private PrpayOperCd prpayOperCd;

    /**
     * 선불카드 상태 코드
     */
    @Enumerated(EnumType.STRING)
    private PrpaySttsCd prpaySttsCd;

//	/**
//	 * 병합한 부모 선불카드 일련번호
//	 */
//	private Long joinPrpaySn;
//
//	/**
//	 * 병합한 자식 선불카드 목록
//	 */
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "joinPrpaySn", nullable = true, updatable = false, insertable = false)
//	@org.hibernate.annotations.ForeignKey(name = "none")
//	private List<Prpay> subPrpayList;

    /**
     * 사용 가능 금액
     */
    private Integer usePosblAmt = 0;
    /**
     * 사용 가능 금액 중 포인트 금액
     * <p>
     * 선불카드 잔액 중 포인트 부분 금액(usePosblAmt - pointAmt = 현금)
     */
    private Integer pointAmt = 0;

    /**
     * 최대 충전 가능 금액
     */
    private Integer chrgMaxAmt = 0;
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
    private UserInfo userInfo;


    /**
     * 정렬순서
     */
    private Integer sort;

    /**
     * 선불카드 종류
     */
    private PrpayGoods prpayGoods;


    /**
     * 선불카드 발급 이력
     */
    private PrpayIssu prpayIssu;

    /**
     * 빌링
     */
    private Billing billing;

}