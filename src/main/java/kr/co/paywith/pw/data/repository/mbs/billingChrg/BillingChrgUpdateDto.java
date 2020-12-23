package kr.co.paywith.pw.data.repository.mbs.billingChrg;


import kr.co.paywith.pw.data.repository.mbs.billing.Billing;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 빌링(정기결제 정보) 이용한 충전규칙
 */
@Data
public class BillingChrgUpdateDto {

    /**
     * 빌링 충전규칙 일련번호
     */
    private Integer id;

    /**
     * 기준 금액(이 금액 미만이 되면 결제)
     */
    @Min(0)
    private Integer stdLowerAmt;

    /**
     * 결제 시간(값이 없을 경우 즉시, 값이 있을 경우 해당 시간에 결제)
     */
    @Min(0)
    @Max(24)
    private Integer setleHour;

    /**
     * 결제 금액
     */
    @Min(1)
    private Integer setleAmt;

    /**
     * 바로 결제를 하지 않고 확인 메시지 전달
     */
    private Boolean reqConfirmFl = false;

    /**
     * 빌링
     */
    @ManyToOne
    private Billing billing;



//    /**
//     * 선불카드
//     */
//    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "prpaySn", insertable = false, updatable = false)
//    private Prpay prpay;
//
//    /**
//     * 선불카드 일련번호
//     */
//    @Column(name = "prpaySn")
//    private Long prpaySn;


    
}
