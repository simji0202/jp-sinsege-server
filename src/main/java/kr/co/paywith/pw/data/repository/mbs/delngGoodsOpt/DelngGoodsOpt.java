package kr.co.paywith.pw.data.repository.mbs.delngGoodsOpt;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import kr.co.paywith.pw.data.repository.mbs.delngGoods.DelngGoods;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 거래 상품 옵션
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"delngGoods"})
public class DelngGoodsOpt {

    /**
     * 거래 상품 옵션 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 거래 상품 옵션 개별 금액(단가)
     */
    private Integer delngAmt;

    /**
     * 쿠폰 적용 금액. delngAmt - cpnAmt 만큼 결제가 되어야 한다.
     *
     * 조작을 막기 위해 cpn의 할인율과 금액, goods를 확인해서 계산 후 서버에서 입력한다.
     */
    private Integer cpnAmt;

//    /**
//     * 거래 상품(옵션)
//     */
//    @ManyToOne
//    private Goods goods;

    /**
     * 거래 상품(옵션) 수량
     */
    @Max(value = 99)
    @Min(value = -99)
    private Integer goodsCnt;

    /**
     * 거래 상품
     */
    @ManyToOne
    private DelngGoods delngGoods;

//    /**
//     * 사용한 상품(옵션) 쿠폰
//     */
//    @OneToOne
//    private Cpn cpn;

}
