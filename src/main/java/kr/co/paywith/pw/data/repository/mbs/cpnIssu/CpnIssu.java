package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import java.util.ArrayList;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpnIssuRule.CpnIssuRule;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 쿠폰 발급 (대장)
 *
 * CpnIssuRule에 의해서 발급, 혹은 관리자가 대상을 임의로 지정해서 발급.
 *
 * 선불카드 충전, 선불카드 사용, 상품 구매, 등급별로 정기 발급 등의 이벤트를 고려하여 개발
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
    @OneToMany(mappedBy = "cpnIssu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cpn> cpnList = new ArrayList<>();

    /**
     * 쿠폰 발급 규칙
     */
    @ManyToOne
    private CpnIssuRule cpnIssuRule;

//    /**
//     * 충전 이력
//     * 충전으로 발급한 쿠폰일 때 사용
//     */
//    @OneToOne
//    private Chrg chrg;
//
//    /**
//     * 사용 이력
//     * 사용으로 발급한 쿠폰일 때 사용
//     */
//    @OneToOne
//    private Use use;
//
//    /**
//     * 스탬프 이력
//     * 스탬프 적립으로 발급한 쿠폰일 때 사용
//     *
//     * ex> 스탬프 직접 적립 혹은 Use 추가해서 적립 -> stampRule.SI(바로발급) 인 경우 cpnIssu를 생성하면서 여기에 연결
//     */
//    @OneToOne
//    private StampHist stampHist;

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


    public void setCpnList(List<Cpn> cpnList) {

        if (cpnList != null) {
            this.cpnList.clear();
            this.cpnList.addAll(cpnList);

        } else {
            this.cpnList = cpnList;
        }
    }


}
