package kr.co.paywith.pw.data.repository.od.ordrPosIf;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.OrdrStatus;
import kr.co.paywith.pw.data.repository.enumeration.PosIfResultCdType;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class OrdrPosIf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    private LocalDateTime sendDttm;

    private LocalDateTime recvDttm;

    @Enumerated(EnumType.STRING)
    private OrdrStatus ordrStatCd;

    @Size(max = 2000)
    private String senddata;

    @Size(max = 2000)
    private String recvdata;

    @Enumerated(EnumType.STRING)
    private PosIfResultCdType resultCd;

    @Size(max = 2000)
    private String errMsg;

    private String apiUrl;

    private Integer mobileOrderNo;


//    @ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "brand_sn", insertable = false, updatable = false)
//    private Brand brand;
//
//    @Column(name = "brand_sn" , nullable = true, updatable = false)
//    private Integer brandSn;

    @ManyToOne
    private OrdrHist ordrHist;

    @CreationTimestamp
    private LocalDateTime regDttm;

    /**
     * 추가한 관리자
     */
    private String createBy;

    /**
     * 변경한  관리자
     */
    private String updateBy;
}