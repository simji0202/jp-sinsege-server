package kr.co.paywith.pw.data.repository.mbs.chrg;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.enumeration.ChrgType;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChrgDto {

    /**
     * 서버 승인번호
     */
    private String confmNo;

    /**
     * 단말기 영수증 번호 (POS 사용시만 사용 )
     */
    private String trmnlDelngNo;

    /**
     * 결제 장소(채널)
     */
    private ChrgType chrgType; // 매장 / PG 구분

    /**
     * 충전일시. 매장 거래 시간 등 실제 거래시간.
     *
     * 무통장 결제 등 결제 결과를 나중에 받는다면 chrgDttm POST 타이밍에 없을 수 있다
     *
     * regDttm만 일단 입력되며 우선 저장, 무통장입금 결과 수신 시 chrgDttm 저장하며 후속처리(잔액 충전, 쿠폰 발급 등)를 한다
     */
    private ZonedDateTime chrgDttm;

    /**
     * 충전 금액
     */
    private int chrgAmt = 0;

    /**
     * 실제 결제 금액. 이벤트 등으로 실제 결제한 금액보다 더 충전해 줄 수 있으므로, 별도로 필드 관리
     */
    private int payAmt = 0;

    /**
     * 결제 승인번호(POS, PG 업체 쪽에서 받는 번호)
     */
    private String setleConfmNo;

    /**
     * 충전 회원(금액 가산되는 회원)
     */
    private UserInfo userInfo;

    /**
     * 가맹점 단말기. 매장에서 저장한 충전이라면 단말기 정보가 있음 (POS, PW)
     */
    private Integer mrhstTrmnlId;

    /**
     * 충전 매장 아이디
     */
    private String mrhstId;

    /**
     * 충전 매장 이름
     */
    private String mrhstNm;

//    /**
//     * PG 결제
//     */
//    private Pay pay;

}
