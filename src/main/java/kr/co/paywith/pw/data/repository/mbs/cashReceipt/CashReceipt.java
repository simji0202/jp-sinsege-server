package kr.co.paywith.pw.data.repository.mbs.cashReceipt;


import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

// kms: 시스템 내부에서 생성하므로 POST api, DTO 등 불필요

/**
 * 현금영수증
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CashReceipt {
    /**
     * 현금영수증 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
//    @OneToOne
//    private Delng delng;

//  /**
//   * 사용이력 일련번호
//   */
//  @Column
//  private Long useSn;

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
