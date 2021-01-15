package kr.co.paywith.pw.data.repository.od.staff;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.StaffWorkType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
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
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    private String staffNm;

    private String staffMobile;

    private String nicknm;


    private String prImgUrl;

    private String staffCn;

    @Enumerated(EnumType.STRING)
    private StaffWorkType staffWorkTypeCd;

    private String regId;

    private String updtId;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Mrhst mrhst;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

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