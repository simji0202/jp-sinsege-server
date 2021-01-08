package kr.co.paywith.pw.data.repository.mbs.stamp;

import kr.co.paywith.pw.data.repository.enumeration.StampSttsTypeCd;
import lombok.Data;

@Data
public class StampUpdateDto {
    /**
     * 스탬프 상태 코드
     */
    private StampSttsTypeCd stampSttsTypeCd = StampSttsTypeCd.RSRV;

}