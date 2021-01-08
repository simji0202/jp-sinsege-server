package kr.co.paywith.pw.data.repository.mbs.refund;

import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import lombok.Data;

import javax.persistence.OneToOne;

@Data
public class RefundUpdateDto {

    /**
     * 결제
     */
    @OneToOne
    private DelngPayment delngPayment;

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