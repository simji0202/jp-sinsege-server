package kr.co.paywith.pw.data.repository.mbs.cpn;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 가맹점
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Cpn {


    /**
     * 쿠폰 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 쿠폰 번호
     */
    private String cpnNo;



    /**
     * 쿠폰 상태 코드
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private CpnSttsCd cpnSttsCd;

    /**
     * 쿠폰 종류
     */
    @ManyToOne
    private CpnMaster cpnMaster;

    /**
     * 쿠폰 종류 일련번호
     */
    @Column(nullable = true)
    private Integer cpnMasterSn;

    // TODO 쿠폰 부하 심하면 LAZY 전환 -> 관리자 표시 수정
    /**
     * 쿠폰 소지 회원
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UserInfo userInfo;


    /**
     * 쿠폰 발급(대장)
     */
    @ManyToOne
    private CpnIssu cpnIssu;

    /**
     * 확인 여부
     */
    private Boolean readFl = false;


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


    @NameDescription("갱신담당자")
    private String updateBy;

    @NameDescription("등록담당자")
    private String createBy;
}
