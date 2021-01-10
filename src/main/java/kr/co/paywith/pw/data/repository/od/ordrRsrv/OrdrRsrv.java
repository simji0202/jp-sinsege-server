package kr.co.paywith.pw.data.repository.od.ordrRsrv;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.ordrHist.OrdrHist;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
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
public class OrdrRsrv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;


    private String rsrvUserNm;

    private String rsrvMobile;

    private String rsrvEmail;

    private String rsrvReqCn;

    private String rsrvDt;

    private String rsrvStartHour;

    private String rsrvEndHour;

    private String rsrvStartMinute;

    private String rsrvEndMinute;

    private Integer attendCnt;

    private Integer attendKidCnt;

    private Integer attendBabyCnt;

    private String regId;

    @CreationTimestamp
    private LocalDateTime regDttm;

    private String updtId;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

    @ManyToOne
    private Brand brand;

    @OneToOne
    private OrdrHist ordrHist;




}