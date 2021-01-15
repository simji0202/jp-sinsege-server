package kr.co.paywith.pw.data.repository.mbs.goods;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.goodsApply.GoodsApply;
import kr.co.paywith.pw.data.repository.mbs.goodsGrp.GoodsGrp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 상품
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Goods {

  /**
   * 상품 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 상품 코드 (POS 연동)
   */
  @CsvBindByName(column = "Code")
  private String goodsCd;

  /**
   * 상품 명
   */
  @CsvBindByName(column = "Name")
  private String goodsNm;
  /**
   * 상품 내용(소개)
   */
  @Lob
  @CsvBindByName(column = "Description")
  private String goodsCn;
  /**
   * 상품 금액
   */
  @CsvBindByName(column = "Price")
  private Integer goodsAmt;
  /**
   * 사용 여부
   */
  @CsvBindByName(column = "Active")
  private Boolean activeFl;

  /**
   * 구매시 스탬프 추가 개수
   */
  @CsvBindByName(column = "Stamp")
  private Integer stampPlusCnt;

  /**
   * 구매시 점수 추가 양
   */
  @CsvBindByName(column = "Score")
  private Integer scorePlusCnt = 1;

  /**
   * 상품 그룹(카테고리)
   */
  @OneToOne
  private GoodsGrp goodsGrp;

  /**
   * 상품 이미지 웹 경로
   */
  @Column(nullable = true)
  private String imgUrl;

  /**
   * 적용 대상 상품 목록.
   *
   * ex. 곱빼기 상품은 goodsApplyList에 짜장, 짬뽕 등이 Goods 로 있어야 한다
   */
  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  private List<GoodsApply> goodsApplyList;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private ZonedDateTime updtDttm;

  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;
}
