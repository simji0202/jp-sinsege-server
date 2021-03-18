package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import kr.co.paywith.pw.data.repository.enumeration.GoodsStockHistType;
import lombok.Data;

/**
 * 상품
 */
@Data
public class GoodsStockHistDto {

  @Max(value = 999999, message = "한번에 처리할 수 있는 재고는 999999 까지 입니다")
  private Integer cnt = 0;

  /**
   * 재고 증가, 감소 등 재고 이력 상태
   */
  @NotNull(message = "필수 값입니다")
  private GoodsStockHistType goodsStockHistType;

  /**
   * 상품 재고
   */
  @ManyToOne
  private GoodsStockDto goodsStock;

}

@Data
class GoodsStockDto {
  // id 가 있거나  goodsId + mrhstId 가 있어야 함

  private Integer id;

  private Integer goodsId;

  private Integer mrhstId;


}