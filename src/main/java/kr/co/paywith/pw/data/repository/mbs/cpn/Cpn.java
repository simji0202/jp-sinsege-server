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
     * 쿠폰 일련번호 (KEY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 쿠폰 번호(바코드 16자리). 쿠폰을 최초 열람할때 번호 생성. 예전 로직과는 순서가 다르므로 참고
     */
    private String cpnNo;

    /**
     * 쿠폰 상태 코드
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private CpnSttsCd cpnSttsCd = CpnSttsCd.AVAIL;

    /**
     * 쿠폰 종류 ( 무료쿠폰, 활인쿠폰, 상품쿠폰, 금액 쿠폰  등 )
     */
    @ManyToOne
    private CpnMaster cpnMaster;

    /**
     * 회원 ( 쿠폰 소유자 )
     */
    @ManyToOne
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


    @NameDescription("변경 일시")
    @CreationTimestamp
    private ZonedDateTime regDttm;

    @NameDescription("수정 일시")
    @UpdateTimestamp
    private ZonedDateTime updtDttm;


    @NameDescription("갱신담당자")
    private String updateBy;

    @NameDescription("등록담당자")
    private String createBy;
}