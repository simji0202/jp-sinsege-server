package kr.co.paywith.pw.data.repository.mbs.pointRule;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointCutTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.PointRuleTypeCd;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PointRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /**
     * 포인트 적립 규칙 구분코드
     */
    @Enumerated(EnumType.STRING)
    private PointRuleTypeCd pointRuleTypeCd;

    /**
     * 포인트 적립할 때 기준으로 할 최소 금액
     * <p>
     * ex> minAmt이상 충전해야 포인트 적립
     */
    private Integer minAmt;

    /**
     * 포인트 변환 비율
     */
    private Float ratioValue;

    /**
     * 소수점 처리 규칙 코드
     */
    @Enumerated(EnumType.STRING)
    private PointCutTypeCd pointCutTypeCd;

    /**
     * 적용 여부
     */
    // kms: 기본값 설정
    private Boolean activeFl = false;

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

    /**
     * 회원 등급
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
