package kr.co.paywith.pw.data.repository.mbs.delng;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DelngGoodsDto {

  /**
   * 상품ID
   */
  private Integer goodsId;

  /**
   * 상품명
   */
  private String goodsNm;

  /**
   * 거래 상품 수량
   */
  private Integer goodsCnt;

  /**
   * 상품 금액
   */
  private Integer goodsAmt;

  /**
   * 옵션들
   */
  private List<DelngGoodsOpt> delngGoodsOptList = new ArrayList<>();

}
