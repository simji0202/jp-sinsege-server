package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.PosFnType;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 가맹점 단말기.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class MrhstTrmnl {

    /**
     * 가맹점 단말기 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 가맹점
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Mrhst mrhst;

    /**
     * 단말기 번호. 외부 시스템 연동에 사용
     */
    private String trmnlNo;

    /**
     * 단말기 명
     */
    private String trmnlNm;

    /**
     * 사용 여부
     */
    private Boolean activeFl = true;

    /**
     * 웹포스 로그인 아이디
     */
    private String userId;

    /**
     * 웹포스 로그인 암호
     */
    @JsonIgnore
    private String userPw;

    /**
     * 로그인 정보. 로그인 시 클라이언트에서 UUID를 보내오면 저장. 로그아웃 하면 삭제한다.(중복 로그인 방지)
     */
    private String userUuid;

    /**
     * 푸시 키. 웹포스 앱 버전등에 사용
     */
    private String pushKey;

    /**
     * 푸시 수신 여부
     */
    private Boolean pushFl = true;

    /**
     * POS에서 사용가능한 기능
     */
    @Column(length = 10)
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<PosFnType> posFnTypeList = new ArrayList<>();

    /**
     * 최근 로그인 일시
     */
    private LocalDateTime lastLoginDttm;

    /**
     * 최근 로그인 IP
     */
    private String lastLoginIp;


    /**
     * 등록 일시
     */
    @CreationTimestamp
    private LocalDateTime regDttm;

    /**
     * 수정 일시
     */
    @UpdateTimestamp
    private LocalDateTime updtDttm;

    /**
     * 관리자타입
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AdminRole> roles;


    /**
     * 추가한 관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String createBy;

    /**
     * 변경한  관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String updateBy;


}
