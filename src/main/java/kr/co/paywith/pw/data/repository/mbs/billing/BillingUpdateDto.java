package kr.co.paywith.pw.data.repository.mbs.billing;

import kr.co.paywith.pw.data.repository.enumeration.PwCorpType;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

/**
 * 빌링(정기결제 정보)
 */
@Data
public class BillingUpdateDto {

    /**
     * 빌키 별명
     */
    private String billNm;

    /**
     * 빌키 암호(Optional)
     */
    private String billPw;

    /**
     * 카드사 명
     */
    private String corpNm;

    /**
     * 카드사 코드
     */
    private PwCorpType corpCd;

    /**
     * 카드 번호
     */
    private String cardNo;

    /**
     * 원본 업체 코드
     */
    private String insttCd;

    /**
     * 회원
     */
    private UserInfo userInfo;

    /**
     * 브랜드PG
     */
//    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//    private BrandPg brandPg;


}
