package kr.co.paywith.pw.data.repository.mbs.pointRsrvRule;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.data.repository.enumeration.PointCutType;
import kr.co.paywith.pw.data.repository.enumeration.PointRsrvRuleType;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointRsrvRuleDto {

    /**
     * 포인트 적립 규칙 구분코드
     */
    @Enumerated(EnumType.STRING)
    private PointRsrvRuleType pointRsrvRuleType;

    /**
     * 포인트 적립할 때 기준으로 할 최소 금액
     * <p>
     * ex> stdValue이상 충전해야 포인트 적립
     */
    private Integer stdValue;

    /**
     * 포인트 변환 비율
     */
    private Integer rsrvRatio = 0;

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
     * 회원 등급
     */
    @ManyToOne
    private Grade grade;

}
