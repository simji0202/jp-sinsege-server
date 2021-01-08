package kr.co.paywith.pw.data.repository.mbs.delng;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.delngGoods.DelngGoods;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class DelngDto {

    /**
     * 단말기 번호
     */
    private String trmnlNo;

    /**
     * 단말기 영수증 번호
     */
    private String trmnlDelngNo;

    /**
     * 사용 구분 코드
     */
    private DelngTypeCd delngTypeCd;

    /**
     * 거래 일시. 현장에서 실제 거래가 발생한 시각
     */
    private ZonedDateTime delngDttm = ZonedDateTime.now();

    /**
     * 사용 금액.
     */
    private Integer delngAmt;

    /**
     * 금액 쿠폰으로 할인 받는 금액.
     */
    private Integer cpnAmt;

    /**
     * 사용한 금액 쿠폰
     */
    private Cpn cpn;

    /**
     * 포인트 사용 금액
     */
    private Integer delngPointAmt = 0;

    /**
     * 회원
     */
    private UserInfo userInfo;

    /**
     * 가맹점
     */
    private Mrhst mrhst;

    /**
     * 거래 상세(물품) 목록
     */
    private List<DelngGoods> delngGoodsList = new ArrayList<>();


    private List<DelngPayment> delngPaymentList = new ArrayList<>();

}
