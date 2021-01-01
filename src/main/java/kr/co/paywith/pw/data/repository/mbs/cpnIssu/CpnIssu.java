package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpnRule.CpnRule;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 쿠폰 발급(대장)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CpnIssu {

    /**
     * 쿠폰 발급 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 쿠폰 발급 명
     */
    private String cpnIssuNm;

    /**
     * 쿠폰 표시(회원에게 노출) 일시
     */
    private ZonedDateTime showDttm = ZonedDateTime.now();

    /**
     * 쿠폰 유효 시작 일시
     */
    private ZonedDateTime validStartDttm = ZonedDateTime.now();

    /**
     * 쿠폰 유효 종료 일시
     */
    private ZonedDateTime validEndDttm = ZonedDateTime.now().plusDays(30);

    /**
     * 쿠폰 발급 수량
     */
    private Integer issuCnt;

    /**
     * 쿠폰 종류
     */
    @ManyToOne
    private CpnMaster cpnMaster;

    /**
     * 발급 쿠폰 목록
     */
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Cpn> cpnList;

    /**
     * 쿠폰 발급 규칙
     */
    @ManyToOne
    private CpnRule cpnRule;

    /**
     * 충전 이력
     * 충전으로 발급한 쿠폰일 때 사용
     */
    @ManyToOne
    private Chrg chrg;

    /**
     * 사용 이력
     * 사용으로 발급한 쿠폰일 때 사용
     */
    @ManyToOne
    private Use use;

    /**
     * 스탬프 이력
     * 스탬프 적립으로 발급한 쿠폰일 때 사용
     */
    @ManyToOne
    private StampHist stampHist;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;


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
