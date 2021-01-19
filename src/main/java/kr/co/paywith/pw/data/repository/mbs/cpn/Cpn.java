package kr.co.paywith.pw.data.repository.mbs.cpn;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssuSerializer;
import kr.co.paywith.pw.data.repository.mbs.cm.CpnMaster;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 쿠폰
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
     * 회원 ( 쿠폰 소유자 )
     */
    @OneToOne
    private UserInfo userInfo;

    /**
     * 쿠폰 발급(대장)
     *  CpnIssu
     *     cpu1, cpn2 .....
     */
    @ManyToOne
    @JsonSerialize(using = CpnIssuSerializer.class)
    private CpnIssu cpnIssu;

    /**
     * 확인 여부
     */
    private Boolean readFl = false;


    /**
     * 쿠폰 종류
     */
    @ManyToOne
    private CpnMaster cpnMaster;



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

    /**
     * 쿠폰이 사용가능한 상태인지 여부. 사용처가 많아서 pojo에 필드화
     *
     * @return 사용 가능하면 true
     */
    @Transient
    public boolean isAvail() {
        if (this.cpnSttsCd.equals(CpnSttsCd.AVAIL)) {
            if (this.cpnIssu != null && this.cpnIssu.getValidEndDttm().isAfter(ZonedDateTime.now())) {
                return true;
            }
        }
        return false;
    }
}
