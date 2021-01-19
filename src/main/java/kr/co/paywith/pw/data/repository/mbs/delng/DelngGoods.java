package kr.co.paywith.pw.data.repository.mbs.delng;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DelngGoods {

    /**
     * 상품ID
     */
    private Integer goodsId;

    /**
     * 상품명 (쿠폰명)
     */
    private String goodsNm;

    /**
     * 거래 상품 수량
     */
    private int goodsCnt;

    /**
     * 상품 금액 ( 가격 )
     */
    private int goodsAmt;

    /**
     * 옵션들
     */
    private List<DelngGoodsOpt> delngGoodsOptList = new ArrayList<>();


}
