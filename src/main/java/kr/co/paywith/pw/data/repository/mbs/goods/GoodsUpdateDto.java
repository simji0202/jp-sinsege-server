package kr.co.paywith.pw.data.repository.mbs.goods;

import com.opencsv.bean.CsvBindByName;
import java.util.List;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.mbs.goodsGrp.GoodsGrp;
import lombok.Data;

/**
 * 상품
 */
@Data
public class GoodsUpdateDto {


  /**
   * 상품 코드 (POS 연동)
   */
  private String goodsCd;

  /**
   * 상품 명
   */
  private String goodsNm;
  /**
   * 상품 내용(소개)
   */
  private String goodsCn;
  /**
   * 상품 금액
   */
  private int goodsAmt;

  /**
   * 사용 여부
   */
  private Boolean activeFl;

  /**
   * 구매시 스탬프 추가 개수
   */
  private int plusStampCnt;

  /**
   * 구매시 점수 추가 양
   */
  private int plusScore = 1;

  /**
   * 상품 그룹(카테고리)
   */
  @OneToOne
  private GoodsGrp goodsGrp;


  /**
   * 상품 옵션
   */
  private List<GoodsOpt> goodsOptList;

  /**
   * 상품 이미지 웹 경로
   */
  private String imgUrl;




}
