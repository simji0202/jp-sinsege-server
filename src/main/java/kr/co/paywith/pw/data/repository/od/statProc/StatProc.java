package kr.co.paywith.pw.data.repository.od.statProc;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.OrdrStatus;
import kr.co.paywith.pw.data.repository.enumeration.StatProcType;
import kr.co.paywith.pw.data.repository.enumeration.UserType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class StatProc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;


    @Enumerated(EnumType.STRING)
    private OrdrStatus ordrStatCd;

    private String statProcNm;

    private Integer sort = 0;

    private Long parentId;

    @OneToMany
    private List<StatProc> subStatProcList;

    @Size(max = 30)
    private String regUserId;

    @ManyToOne
    private Brand brand;

    /**
     * 다음 단계로 처리 가능한 UserType
     */
    @Enumerated(EnumType.STRING)
    private UserType confirmUserTypeCd;

    /**
     * Proc 구분
     */
    @Enumerated(EnumType.STRING)
    private StatProcType statProcTypeCd;

    /**
     * 이 상태가 되면 바로 취소를 진행한다. 설정 실수를 막기 위해 cancelStatFl == true 인지도 확인 할 것.
     *
     * 둘 다 true 라면 다른 상태로 복원할 수 없도록 한다.
     */
    private Boolean instantRefundFl = false;

    /**
     * confirmUserTypeCd 제외한 UserType에 메시지 보낼지 여부
     */
    private Boolean notifSendFl = false;

    /**
     * 메시지 제목. 최초 등록 및 현재 단계에 대한 알림 메시지 보낼 때 사용
     */
    private String msgSj;

    /**
     * 메시지 본문. 최초 등록 및 현재 단계에 대한 알림 메시지 보낼 때 사용.
     *
     * 본문이 없으면 notifSendFl이 true 여도 메시지를 보내지 않는다
     */
    private String msgCn;

    @CreationTimestamp
    private LocalDateTime regDttm;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

    /**
     * 추가한 관리자
     */
    private String createBy;

    /**
     * 변경한  관리자
     */
    private String updateBy;

    /**
     * 취소가능한 UserType 목록
     */
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<UserType> cancelAvailUserTypeCdList;

    @Transient
    @Deprecated
    public Boolean getCancelAvailFl() {
        if (this.parentId == null) {
            return true;
        }
        if (this.getSubStatProcList() != null) {
            for (StatProc statProc : this.getSubStatProcList()) {
                if (statProc.getStatProcTypeCd() != null &&
                        StatProcType.CANCEL.equals(statProc.getStatProcTypeCd())) {
                    return true;
                }
            }
        }
        return false;
    }
}