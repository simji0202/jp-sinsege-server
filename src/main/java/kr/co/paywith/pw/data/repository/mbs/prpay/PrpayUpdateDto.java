package kr.co.paywith.pw.data.repository.mbs.prpay;

import kr.co.paywith.pw.data.repository.enumeration.PrpaySttsCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

@Data
public class PrpayUpdateDto {

    /**
     * 선불카드 이름
     */
    private String prpayNm;

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
     * 선불카드 상태 코드
     */
    @Enumerated(EnumType.STRING)
    private PrpaySttsCd prpaySttsCd;

    /**
     * 사용 가능 금액
     */
    private Integer usePosblAmt;

    /**
     * 사용 가능 금액 중 포인트 금액
     * <p>
     * 선불카드 잔액 중 포인트 부분 금액(usePosblAmt - pointAmt = 현금)
     */
    private Integer pointAmt;

    /**
     * 최대 충전 가능 금액
     */
    private Integer chrgMaxAmt;

    /**
     * 총 충전 금액
     */
    private Integer chrgTotAmt;

    /**
     * 총 사용 금액
     */
    private Integer useTotAmt;

    /**
     * 회원
     */
    private UserInfo userInfo;

    /**
     * 정렬순서
     */
    private Integer sort;


}