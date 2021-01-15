package kr.co.paywith.pw.data.repository.mbs.goods;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.data.repository.mbs.goodsApply.GoodsApply;
import kr.co.paywith.pw.data.repository.mbs.goodsGrp.GoodsGrp;
import lombok.Data;

import javax.persistence.Lob;
import java.util.List;

/**
 * 상품
 */
@Data
public class GoodsUpdateDto {

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
    private Integer stampPlusCnt = 0;

    /**
     * 구매시 점수 추가 양
     */
    @CsvBindByName(column = "Score")
    private Integer scorePlusCnt = 1;

    /**
     * 상품 그룹(카테고리)
     */
    private GoodsGrp goodsGrp;

    /**
     * 상품 이미지 웹 경로
     */
    private String imgUrl;

    /**
     * 적용 대상 상품 목록
     */
    private List<GoodsApply> goodsApplyList;

}
