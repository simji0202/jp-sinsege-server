package kr.co.paywith.pw.data.repository.mbs.delng;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DelingGoodsDto {


    /** 상품ID */
    private Integer goodsId;

    /** 상품명 */
    private String goodsNm;

    /** 거래 상품 수량 */
    private Integer goodsCnt;

    /** 상품 금액 */
    private Integer goodsAmt;

    /** 옵션들  */
    private List<DelngGoodsOpt> delngGoodsOptList = new ArrayList<>();

    @Data
    class DelngGoodsOpt {

        /** 옵션 이름 */
        private String optTitle;

        /** 다중 선택 여부 ( 라디오버튼 : false , 체크박스 : true ) */
        private Boolean multiChoiceFl = false;

        @Data
        class GoodsOptMaster{

            /** 옵션 이름 */
            private String GoodsOptNm;

            /** 옵션 가격 */
            private Integer goodsOptAmt;

        }

    }


}
