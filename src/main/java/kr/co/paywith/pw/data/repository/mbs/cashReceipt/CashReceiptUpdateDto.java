package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import java.time.LocalDateTime;
import javax.persistence.Column;

import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
public class CashReceiptUpdateDto {


    /**
     * 현금영수증 국세청승인번호
     */
    private String orgConfmNo;

    /**
     * 현금영수증 거래일자 yyyyMMdd
     */
    //  @Column(length = 8)
    private String orgTradeDate;

    // kms: DelngPayment로 연결 변경 예정
    // che2 : delng 정보를 참조
    /**
     * 현금영수증 발급 한 사용이력.
     * DelngPaymentType.PRPAY 일 때 현금영수증 발급한다
     */
//      @OneToOne
//      private DelngPayment delngPayment;
    private Delng delng;

    /**
     * 취소 일시
     */
    private LocalDateTime cancelRegDttm;

    /**
     * 취소 전송 예약 여부
     */
    private Boolean cancelSendRsrvFl = false;
}
