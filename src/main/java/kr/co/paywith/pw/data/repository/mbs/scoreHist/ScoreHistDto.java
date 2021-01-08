package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import kr.co.paywith.pw.data.repository.mbs.scoreRule.ScoreRule;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreHistDto {

    /**
     * 점수
     */
    private Integer scoreAmt;

    /**
     * 회원
     */
    private UserInfo userInfo;

    /**
     * 취소 일시
     */
    private ZonedDateTime cancelRegDttm;


    /**
     * 점수 획득 규칙
     */
    private ScoreRule scoreRule;
    /**
     * 점수 획득 규칙 일련번호
     */
    private Integer scoreRuleSn;

    /**
     * 충전 이력
     */
    private Chrg chrg;

    /**
     * 거래 이력
     */
    private Delng delng;

    /**
     * 스탬프 이력
     */
    private StampHist stampHist;


}