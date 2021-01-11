package kr.co.paywith.pw.data.repository.mbs.goodsgrp;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

/**
 * 상품 그룹(카테고리)
 */
@Data
public class GoodsGrpDto {

    /**
     * 상품 그룹 일련번호
     */
    private Integer id;

    /**
     * 상품 그룹 명
     */
    private String goodsGrpNm;

    /**
     * 상품 그룹 코드
     */
    private String goodsGrpCd;

    /**
     * 부모 상품 그룹 일련번호
     */
    private Integer parentGoodsGrpSn;

    /**
     * 정렬순서
     */
    private Integer sort;

    private Brand brand;

    /**
     * 사용 여부
     */
    private Boolean activeFl = true;

}
