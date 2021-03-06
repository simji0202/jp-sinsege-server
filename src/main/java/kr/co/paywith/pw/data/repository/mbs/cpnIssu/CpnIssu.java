package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import java.util.ArrayList;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnIssuRule.CpnIssuRule;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime showDttm = LocalDateTime.now();

    /**
     * 쿠폰 유효 시작 일시
     */
    private LocalDateTime validStartDttm = LocalDateTime.now();

    /**
     * 쿠폰 유효 종료 일시
     */
    private LocalDateTime validEndDttm = LocalDateTime.now().plusDays(30);

    /**
     * 쿠폰 발급 수량
     */
    private Integer issuCnt;

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

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private LocalDateTime regDttm;

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
