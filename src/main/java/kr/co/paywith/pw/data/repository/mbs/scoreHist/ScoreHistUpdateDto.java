package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.payment.Payment;
import kr.co.paywith.pw.data.repository.mbs.scoreRule.ScoreRule;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ScoreHistUpdateDto {

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
     * 관리자
     */
    private Admin admin;
    // @Column(nullable = true)
    /**
     * 관리자 일련번호
     */
    private Integer adminSn;


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
     * 결제 이력
     */
    private Payment payment;

    /**
     * 결제 이력 일련번호
     */
    private Long paymentSn;

    /**
     * 사용 이력
     */
    private Use use;


    /**
     * 스탬프 이력
     */
    private StampHist stampHist;


}