package kr.co.paywith.pw.data.repository.mbs.cpn;

import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 가맹점
 */
@Data
public class CpnUpdateDto {

    /**
     * 쿠폰 상태 코드
     */
    @Enumerated(EnumType.STRING)
    private CpnSttsCd cpnSttsCd;

}
