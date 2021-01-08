package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import kr.co.paywith.pw.data.repository.enumeration.PaymentChnlCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentMthdCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentSttsCd;
import kr.co.paywith.pw.data.repository.mbs.brandPg.BrandPg;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelngPaymentDto {

    /**
     * 서버 승인번호
     */
    private String confmNo;

    /**
     * 단말기 영수증 번호
     */
    private String trmnlDelngNo;

    /**
     * 결제 금액
     */
    private Integer paymentAmt = 0;

    /**
     * 결제 승인번호(PG 쪽에서 받는 번호)
     */
    private String setleConfmNo;

    /**
     * 결제 방법
     */
    @Enumerated(EnumType.STRING)
    private PaymentMthdCd paymentMthdCd;

    /**
     * 업체 코드. 현금과 각 카드사를 구분하는 코드. PG업체마다 다를 수 있음
     */
    private String paymentSetleInsttCd; // 현금 / 카드사 구분

    /**
     * 결제 장소(채널)
     */
    @Enumerated(EnumType.STRING)
    private PaymentChnlCd paymentChnlCd;

    /**
     * 결제 진행 상태. PG는 결제 이전 PRE 상태로 먼저 추가
     */
    private PaymentSttsCd paymentSttsCd = PaymentSttsCd.PRE; // 결제 상태

    /**
     * pw-proxy 연동에서 사용하는 거래 ID
     */
    private String proxyTid;

    /**
     * pw-proxy 연동해서 받은 결과 중 비회원 식별을 위한 이메일(결제 완료 과정에서 입력하는 이메일)
     */
    private String emailAddr;

    /**
     * 승인일시. 매장 거래 시간 등 실제 거래시간
     */
    private ZonedDateTime confmDttm = ZonedDateTime.now(); // 매장 거래 시간

    /**
     * 회원
     */
    private UserInfo user;

    /**
     * 이 결제로 구매한 사용(구매)이력
     * <p>
     * 구매를 하지 않고 결제만 했다면 취소(환불) 처리 등을 해야 한다
     */
    private UserInfo useUser;

    /**
     * 브랜드PG
     */
    private BrandPg brandPg;

}