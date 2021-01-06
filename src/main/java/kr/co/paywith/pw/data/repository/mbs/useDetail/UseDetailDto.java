package kr.co.paywith.pw.data.repository.mbs.useDetail;

import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import lombok.Data;

/**
 * 사용 상세
 */
@Data
public class UseDetailDto {

    /**
     * 사용 상세 개별 금액
     */
    private Integer useAmt;

    /**
     * 사용 상세 개별 상품
     */
    private Goods goods;

    /**
     * 사용 상세 개별 상품 수량
     */
    private Integer goodsCnt;
}
