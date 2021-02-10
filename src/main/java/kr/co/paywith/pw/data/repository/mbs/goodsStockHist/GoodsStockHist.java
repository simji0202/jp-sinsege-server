package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.data.repository.enumeration.GoodsStockHistType;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStock;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 상품 재고 이력
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class GoodsStockHist {

  /**
   * 상품 재고 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private int cnt = 0;

  /**
   * 재고 증가, 감소 등 재고 이력 상태. 재고가 줄 때에는 음수로 설정한다
   */
  private GoodsStockHistType goodsStockHistType;

  /**
   * 상품 재고
   */
  @ManyToOne
  private GoodsStock goodsStock;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private LocalDateTime regDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private LocalDateTime updtDttm;

  /**
   * 추가한 관리자
   */
  private String createBy;

  /**
   * 변경한 관리자
   */
  private String updateBy;

  /**
   * 거래 시 이력 작성 등, 비즈니스 로직에서 단축하여 생성
   */
  public GoodsStockHist(
      GoodsStockHistType goodsStockHistType,
      int cnt,
      Integer goodsId,
      Integer mrhstId
  ) {
    this.goodsStockHistType = goodsStockHistType;
    this.cnt = cnt;
    this.goodsStock = new GoodsStock();
    this.goodsStock.setGoodsId(goodsId);
    this.goodsStock.setMrhstId(mrhstId);
  }
}
