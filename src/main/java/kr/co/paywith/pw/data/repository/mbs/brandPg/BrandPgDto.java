package kr.co.paywith.pw.data.repository.mbs.brandPg;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PgTypeCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandPgDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * PG코드
     * <p>
     * api : mallId 등
     * <p>
     * pw-proxy : mallId를 등록 후 발급 받는 pgId
     */
    private String pgCd;

    /**
     * pg암호
     * <p>
     * pw-proxy에서 결제확인, 취소 시에 사용하는 암호(pwPw)
     */
    private String pgKey;

    /**
     * PG 이름
     * <p>
     * API : 경로로도 사용하는 영문 pg 약어(kicc)
     * <p>
     * pw-proxy : PG 종류 식별을 위해 사용(KICC / NICE)
     */
    private String pgNm;

    /**
     * PG 구분. pw-server 내에서 PG 구분을 위해 사용하고 pw-proxy 에 식별 위해 전달(KICC / NICE)
     */
    @Enumerated(EnumType.STRING)
    private PgTypeCd pgTypeCd;

}