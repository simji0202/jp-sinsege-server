package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PrpayHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Data
public class PrpayHistUpdateDto {

    private Long prpayHisnSn;

    private ZonedDateTime histDttm = ZonedDateTime.now();

    /**
     * 충전 이력
     */
    @ManyToOne
    private Chrg chrg;
    /**
     * 사용 이력
     */
    @ManyToOne
    private Use use;


    @ManyToOne
    private Prpay prpay;


    @Enumerated(EnumType.STRING)
    private PrpayHistTypeCd histTypeCd;
}