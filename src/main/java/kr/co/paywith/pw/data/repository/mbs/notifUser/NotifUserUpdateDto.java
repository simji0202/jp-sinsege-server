//package kr.co.paywith.pw.data.repository.mbs.notifUser;
//
//import kr.co.paywith.pw.common.NameDescription;
//import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
//import kr.co.paywith.pw.data.repository.user.user.UserInfo;
//import lombok.Data;
//
//import javax.persistence.ManyToOne;
//import java.time.LocalDateTime;
//
//@Data
//public class NotifUserUpdateDto {
//
//    @NameDescription("식별번호")
//    private Integer id;
//
//    /**
//     * 회원
//     */
//    @ManyToOne
//    private UserInfo userInfo;
//
//    /**
//     * 푸시 메시지
//     */
//    @ManyToOne
//    private Notif notif;
//
//    /**
//     * 푸시 일련번호
//     */
//    private Integer notifSn;
//
//    /**
//     * 푸시 전송 이력 일련번호
//     */
//    private Integer notifHistSn;
//
//    /**
//     * 전송 여부
//     */
//    private Boolean sendFl;
//
//    /**
//     * 전송 일시
//     */
//    private LocalDateTime sendDttm;
//
//}
