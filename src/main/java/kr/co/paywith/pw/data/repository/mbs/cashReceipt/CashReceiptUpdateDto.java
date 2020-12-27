package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import kr.co.paywith.pw.data.repository.mbs.use.Use;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Data
public class CashReceiptUpdateDto {

    /**
     * 현금영수증 국세청승인번호
     */
    private String orgConfmNo;

    /**
     * 현금영수증 거래일자 yyyyMMdd
     */
    @Column(length = 8)
    private String orgTradeDate;

    /**
     * 현금영수증 발급 한 사용이력
     */
    @ManyToOne
    private Use use;

    /**
     * 사용이력 일련번호
     */
    @Column
    private Long useSn;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

    /**
     * 취소 일시
     */
    private ZonedDateTime cancelRegDttm;

    /**
     * 취소 전송 예약 여부
     */
    private Boolean cancelSendRsrvFl = false;
}