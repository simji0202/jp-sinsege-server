package kr.co.paywith.pw.data.repository.mbs.prpay;

import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrpayDto {

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
     * 생성 일시
     */
    private ZonedDateTime regDttm = ZonedDateTime.now();

    /**
     * 유효 일시
     */
    private ZonedDateTime validDttm;

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
    private Integer chrgMaxAmt;

    /**
     * 선불카드 종류
     */
    private PrpayGoods prpayGoods;

}