package kr.co.paywith.pw.data.repository.mbs.goodsGrp;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.Data;

/**
 * 상품 그룹(카테고리)
 */
@Data
public class GoodsGrpUpdateDto {

    /**
     * 상품 그룹 명
     */
    private String goodsGrpNm;

    /**
     * 상품 그룹 코드
     */
    private String goodsGrpCd;

    // kms: 2차 3차 그룹이 있다면 필드를 어떻게 관리할지 확인 필요
    /**
     * 부모 상품 그룹 일련번호
     */
    private Integer parentGoodsGrpSn;

    /**
     * 정렬순서
     */
    private Integer sort;

    /**
     * 사용 여부
     */
    private Boolean activeFl = false;

    private Brand brand;
}
