// kms: KICC 빌링 기능 이용해서 선불카드 충전을 할 수 없기 때문에 삭제 고려
package kr.co.paywith.pw.data.repository.mbs.billingChrg;


import kr.co.paywith.pw.data.repository.mbs.billing.Billing;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.ZonedDateTime;

/**
 * 빌링(정기결제 정보) 이용한 충전규칙
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BillingChrg {

    /**
     * 빌링 충전규칙 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 기준 금액(이 금액 미만이 되면 결제)
     */
    @Min(0)
    private Integer stdLowerAmt;

    /**
     * 결제 시간(값이 없을 경우 즉시, 값이 있을 경우 해당 시간에 결제)
     */
    @Min(0)
    @Max(24)
    private Integer setleHour;

    /**
     * 결제 금액
     */
    @Min(1)
    private Integer setleAmt;

    /**
     * 바로 결제를 하지 않고 확인 메시지 전달
     */
    private Boolean reqConfirmFl = false;

    /**
     * 빌링
     */
    @ManyToOne
    private Billing billing;



//    /**
//     * 선불카드
//     */
//    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "prpaySn", insertable = false, updatable = false)
//    private Prpay prpay;
//
//    /**
//     * 선불카드 일련번호
//     */
//    @Column(name = "prpaySn")
//    private Long prpaySn;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

    /**
     * 수정 일시
     */
    @UpdateTimestamp
    private ZonedDateTime updtDttm;

    /**
     * 추가한 관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String createBy;

    /**
     * 변경한  관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String updateBy;

}
