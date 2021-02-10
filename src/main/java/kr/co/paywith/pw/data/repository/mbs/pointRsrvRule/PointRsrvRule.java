package kr.co.paywith.pw.data.repository.mbs.pointRsrvRule;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointCutType;
import kr.co.paywith.pw.data.repository.enumeration.PointRsrvRuleType;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PointRsrvRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /**
     * 포인트 적립 규칙 구분코드
     */
    @Enumerated(EnumType.STRING)
    private PointRsrvRuleType pointRsrvRuleType;

    /**
     * 포인트 적립할 때 기준으로 할 최소 금액
     * <p>
     * ex> stdValue 이상 충전해야 포인트 적립
     */
    private int stdValue = 0;

    /**
     * 적립 비율. 0이상이면 비율로 적립 처리( 0 - 100 까지 )
     */
    private int rsrvRatio = 0;

    /**
     * 소수점 처리 규칙 코드
     */
    @Enumerated(EnumType.STRING)
    private PointCutType pointCutType;

    /**
     * 적용 여부
     */
    private Boolean activeFl = false;

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
     * 회원 등급. 등급별로 적립 비율을 다르게 적용할 수 있다
     */
    @ManyToOne
    private Grade grade;

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
