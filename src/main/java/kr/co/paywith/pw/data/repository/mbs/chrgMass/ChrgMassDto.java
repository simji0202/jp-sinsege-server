package kr.co.paywith.pw.data.repository.mbs.chrgMass;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChrgMassDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * 충전금액
     */
    private Integer chrgAmt;

    /**
     * 포인트로 충전 여부(포인트 일때는 chrg 내에 chrgAmt만 가산, setleAmt는 0
     */
    private Boolean pointFl = false;

    /**
     * 충전한 선불카드 매수(=건수)
     */
    private Integer chrgCnt = 0;


    /**
     * 총 충전한 금액
     */
    private Integer chrgTotAmt = 0;


    /**
     * 매장
     */
    private Mrhst mrhst;


}