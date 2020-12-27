package kr.co.paywith.pw.data.repository.mbs.notif;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.NotifTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.SendSttsCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifDto {

    @NameDescription("식별번호")
    private Integer id;

    /**
     * 푸시 메시지 구분
     */
    private NotifTypeCd notifTypeCd = NotifTypeCd.USER;

    /**
     * 푸시 메시지 제목
     */
    private String notifSj;

    /**
     * 푸시 메시지 본문
     */
    private String notifCn;

    /**
     * 푸시 메시지 추가데이터(앱 내 로직에서 사용)
     */
    private String notifData;


    /**
     * 전송 일시
     */
    private ZonedDateTime sendDttm = ZonedDateTime.now();

    /**
     * 전송 상태
     */
    @Enumerated(EnumType.STRING)
    private SendSttsCd sendSttsCd = SendSttsCd.RDY;

    /**
     * 전송 수량
     */
    private Integer sendCnt = 0;

    /**
     * 표시할 이미지 웹 경로
     */
    private String imgUrl;

    /**
     * 광고 푸시 여부
     * <p>
     * 광고 수신 허용한 사람에게 (광고) 문구 붙여 전송
     */
    private Boolean adsFl = false;

    /**
     * 내부자(테스터) 타겟팅 전송 여부
     * <p>
     * 내부자(테스터) 관련 설정이 없어 미사용
     */
    private Boolean sandboxFl = false;

    /**
     * 테스트 전송 여부(FCM에서 정상 응답 받는지 확인)
     */
    private Boolean testFl = false;

    /**
     * 발송 회원 일련번호 목록
     */
    @Transient
    private List<Integer> userSnArr;

}