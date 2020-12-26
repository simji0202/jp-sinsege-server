package kr.co.paywith.pw.data.repository.mbs.questCpnGoods;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.questCpnRule.QuestCpnRule;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class QuestCpnGoodsUpdateDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * 쿠폰 규칙 일련번호
     */
    private Integer cpnRuleSn;
    /**
     * 상품 일련번호
     */
    private Long goodsSn;

    /**
     * 퀘스트 규칙 일련번호
     */
    private QuestCpnRule questCpnRule;

    /**
     * 상품
     */
    private Goods goods;

    /**
     * 상품 개수
     */
    private Integer goodsCnt;
}