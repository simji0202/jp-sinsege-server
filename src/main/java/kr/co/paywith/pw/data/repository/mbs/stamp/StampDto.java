package kr.co.paywith.pw.data.repository.mbs.stamp;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.StampSttsTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StampDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * 만료 일시
     */
    private ZonedDateTime expiredDttm;

    /**
     * 수정 일시
     */
    private ZonedDateTime updtDttm;

    /**
     * 스탬프 상태 코드
     */
    private StampSttsTypeCd stampSttsTypeCd = StampSttsTypeCd.RSRV;
    /**
     * 스탬프를 적립하여 발급한 쿠폰
     */
    private Cpn cpn;

//    /**
//     * 스탬프를 사용했을 때 해당 사용 이력
//     */
//    private Use use;

    /**
     * 스탬프를 소지(획득) 한 회원
     */
    private UserInfo userInfo;

    /**
     * 현재 스탬프를 적립한 이력(이력의 일자를 참고하여 만료 로직 수행)
     */
    private StampHist stampHist;

}