package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentTypeCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelngPaymentDto {


    private Integer id;

    /**
     * 금액 지불 방법.
     * 선불카드, PG 결제, 쿠폰
     */
    private DelngPaymentTypeCd delngPaymentTypeCd;

    /**
     * 결제 금액
     */
    private Integer amt = 0;

    /**
     * 선불카드
     */
    private Integer prpayId;

    /**
     * PG 결제
     */
    private Integer payId;


}