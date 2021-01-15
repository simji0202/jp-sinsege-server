package kr.co.paywith.pw.data.repository.od.rsrvRefund;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.ReserveRefundType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class RsrvRefund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    private Float rsrvRefundRatio;
    private Integer rsrvRefundAmt;
    private Integer rsrvRefundChkTime;

    @Enumerated(EnumType.STRING)
    private ReserveRefundType rsrvRefundCd;

    @Column(length = 30, updatable=false)
    private String regId;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime regDttm;

    @Column(length = 30, insertable=false)
    private String updtId;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mrhst mrhst;


}