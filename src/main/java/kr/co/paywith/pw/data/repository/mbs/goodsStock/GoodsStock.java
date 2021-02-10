package kr.co.paywith.pw.data.repository.mbs.goodsStock;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
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
 * 상품 재고
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class GoodsStock {

  /**
   * 상품 재고 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 현재 매장에 남아있는 재고
   */
  private int cnt = 0;

  /**
   * 상품
   */
  @ManyToOne
  private Goods goods;

  /**
   * 매장
   */
  @ManyToOne
  private Mrhst mrhst;

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
   * create 시에 dto의 값을 가지고 service 에서 사용
   */
  @Transient
  private Integer goodsId;

  /**
   * create 시에 dto의 값을 가지고 service 에서 사용
   */
  @Transient
  private Integer mrhstId;


}
