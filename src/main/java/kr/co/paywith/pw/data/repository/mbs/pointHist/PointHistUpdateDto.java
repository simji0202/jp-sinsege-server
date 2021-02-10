package kr.co.paywith.pw.data.repository.mbs.pointHist;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointHistType;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.pointRsrvRule.PointRsrvRule;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class PointHistUpdateDto {

    /**
     * 포인트 양
     */
    private Integer pointAmt;

    /**
     * 포인트 이력 구분 코드
     */
    @Enumerated(EnumType.STRING)
    private PointHistType pointHistType;

    /**
     * 등록 일시
     */
    private LocalDateTime regDttm;

    /**
     * 포인트 적립 규칙
     */
    private PointRsrvRule pointRsrvRule;
    /**
     * 포인트 적립 규칙 일련번호
     */
    private Integer pointRuleSn;

    /**
     * 취소 일시
     */
    private LocalDateTime cancelRegDttm;

    /**
     * 회원
     */
    private UserInfo userInfo;

    // 포인트 → 충전 및 충전 → 포인트
    /**
     * 관계있는 충전 이력
     * <p>
     * 포인트로 충전한 경우, 충전하여 포인트 적립되는 경우 모두 사용
     */
    private Chrg chrg;


    // 사용금액 비율 -> 포인트
    /**
     * 관계있는 사용 이력
     */
    // che  UserInfo 가 중복 사용중 확인 필요
//	@OneToOne
//	private UserInfo userInfo;


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
