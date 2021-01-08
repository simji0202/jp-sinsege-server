package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import java.time.ZonedDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PrpayHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrpayHistDto {

    @NameDescription("식별번호")
    private Integer id;


    private Long prpayHisnSn;

    private ZonedDateTime histDttm = ZonedDateTime.now();

    /**
     * 충전 이력
     */
    @ManyToOne
    private Chrg chrg;

    /**
     * 선불카드 결제 이력
     */
    @ManyToOne
    private DelngPayment delngPayment;


    @ManyToOne
    private Prpay prpay;


    @Enumerated(EnumType.STRING)
    private PrpayHistTypeCd histTypeCd;
}