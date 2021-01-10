package kr.co.paywith.pw.data.repository.od.ordrPay;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PayType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.cpnMst.CpnMst;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class OrdrPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PayType payTypeCd;

    private Integer payAmt;

    private LocalDate payDttm;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private OrdrHist ordrHist;

    private String confmNo;

    @ManyToOne
    private CpnMst cpnMst;

    /**
     * 취소 거래에 대해 연동서비스에 취소 요청후 응답 받았는지 여부. 취소 거래지만 false 라면 다시 요청을 보내야 함
     */
    private Boolean cancelFl = false;

    @Transient
    private String prpayNo;

    /**
     * pw-server 결제 승인 번호
     *
     * payment를 사용하여 구매를 한다
     */
    private String paymentConfmNo;

    private String cpnNo;
}