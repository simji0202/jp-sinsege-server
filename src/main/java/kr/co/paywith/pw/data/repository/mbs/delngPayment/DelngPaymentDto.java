package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentType;
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
    private DelngPaymentType delngPaymentType;

    /**
     * 결제 금액
     */
    private Integer amt = 0;

    /**
     * 선불, 포인트 사용하는 회원 일련번호
     */
    private Integer userInfoId;

    /**
     * PG 결제
     */
    private Integer payId;


}
