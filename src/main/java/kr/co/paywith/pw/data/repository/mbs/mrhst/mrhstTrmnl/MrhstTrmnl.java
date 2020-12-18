package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.mbs.enumeration.AuthCd;
import kr.co.paywith.pw.data.repository.mbs.enumeration.PosAvailFnCd;
import kr.co.paywith.pw.data.repository.mbs.enumeration.PosTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * 가맹점 단말기
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
     * 단말기 번호
     */
    @CsvBindByName(column = "DeviceNumber")
    private String trmnlNo;

    /**
     * 단말기 명
     */
    @CsvBindByName(column = "DeviceName")
    private String trmnlNm;

    /**
     * 사용 여부
     */
    @CsvBindByName(column = "Active")
    private Boolean activeFl = true;

    /**
     * 주문 서비스 연동 가능 여부
     * 현재 한 매장당 주문서비스 연동 기기는 하나만 있어야 한다.
     */
    private Boolean ordrAvailFl = false;

    /**
     * 단말기 POS 종류
     * 결제 시 ChrgSetleChnlCd가 STR 일 경우 이 타입으로 변경
     * null일 경우에는 STR 상태로 유지
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PosTypeCd posTypeCd = PosTypeCd.STR;

    /**
     * 웹포스 로그인 아이디
     */
    private String userId;

    /**
     * 웹포스 로그인 암호
     */
    private String userPw;

    /**
     * 푸시 키. 웹포스 앱 버전등에 사용
     */
    private String pushKey;

    /**
     * 푸시 수신 여부
     */
    private Boolean pushFl = true;

    /**
     * 충전 기능 숨김 여부 posAvailFnCdList 에 통합
     */
    private Boolean chrgHiddenFl = true;

    /**
     * POS에서 사용가능한 기능
     */
    @Column(length = 10)
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<PosAvailFnCd> posAvailFnCdList;

    /**
     * 최근 로그인 일시
     */
    private ZonedDateTime lastLoginDttm;

    /**
     * 최근 로그인 일시
     */
    private String lastLoginIp;


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


    @NameDescription("권한 코드")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AuthCd authCd;

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
