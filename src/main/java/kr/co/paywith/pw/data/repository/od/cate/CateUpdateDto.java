package kr.co.paywith.pw.data.repository.od.cate;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class CateUpdateDto {

    @NameDescription("식별번호")
    private Integer id;

    private String cateNm;

    private String cateCd;

    private Integer sort;

    @ManyToOne
    private Brand brand;

    private Long parentId;

    private Boolean useFl;

}