package kr.co.paywith.pw.data.repository.mbs.notifHist;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class NotifHistUpdateDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * 매장
     */
    private Mrhst mrhst;


    /**
     * 푸시 메시지
     */
    private Notif notif;


    /**
     * 전송 여부
     */
    private Boolean sendFl;

    /**
     * 전송 일시
     */
    private ZonedDateTime sendDttm;
}