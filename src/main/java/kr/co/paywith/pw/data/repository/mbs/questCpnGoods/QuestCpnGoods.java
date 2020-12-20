package kr.co.paywith.pw.data.repository.mbs.questCpnGoods;


import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.questCpnRule.QuestCpnRule;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 쿠폰 발급 되는 상품 구메 세트
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class QuestCpnGoods {

    /**
     * 퀘스트 상품 세트 일련번호
     */
    @Id
    private Integer questCpnGoodsSn;

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
    @ManyToOne
    private QuestCpnRule questCpnRule;

    /**
     * 상품
     */
    @ManyToOne
    private Goods goods;

    /**
     * 상품 개수
     */
    private Integer goodsCnt;

}
