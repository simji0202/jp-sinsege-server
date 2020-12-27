package kr.co.paywith.pw.data.repository.mbs.refund;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDto {

    @NameDescription("식별번호")
    private Integer id;


    /**
     * 결제
     */
    private Payment payment;

    /**
     * 환불 금액
     */
    private Integer refundAmt = 0;

    /**
     * 무통장입금 했다면 환불받을 은행코드
     */
    private String bankCode;

    /**
     * 무통장입금 했다면 환불받을 계좌번호
     */
    private String accountNum;

    /**
     * 무통장입금 했다면 환불받을 예금주(기록용)
     */
    private String acccountName;

}