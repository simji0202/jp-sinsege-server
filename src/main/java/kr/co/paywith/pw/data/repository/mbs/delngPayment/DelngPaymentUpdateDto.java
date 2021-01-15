package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentChnlCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentMthdCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentSttsCd;
import kr.co.paywith.pw.data.repository.mbs.brandPg.BrandPg;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

@Data
public class DelngPaymentUpdateDto {

    /**
     * 금액 지불 방법.
     * 선불카드, PG 결제, 쿠폰
     */
    private DelngPaymentTypeCd delngPaymentTypeCd;

    /**
     * 결제 금액
     */
    private Integer amt = 0;

    // kms: TODO 선불카드 작성하면 연결
    /**
     * 선불카드
     */
//  @OneToOne
//  private Prpay prpay;

    // kms: TODO PgPay 작성하면 연결
//  /**
//   * PG 결제
//   */
//  @OneToOne
//  private Pay pay;

//  /**
//   * (금액) 쿠폰
//   */
//  @OneToOne
//  private Cpn cpn;


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


}