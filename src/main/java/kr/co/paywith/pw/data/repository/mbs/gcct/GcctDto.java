package kr.co.paywith.pw.data.repository.mbs.gcct;

import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

/**
 * 가맹점
 */
@Data
public class GcctDto {

//    /**
//     * 상품
//     */
//    private MobileGoods mobileGoods;

    /**
     * 받은 회원
     */
    private UserInfo toUserInfo;

    /**
     * 메시지 제목
     */
    private String sj;

    /**
     * 메시지 본문
     */
    private String cn;

    // kms: TODO prx 결제 만들어지면 여기 연결
//    /**
//     * 결제
//     */
//    @ManyToOne
//    private Pay pay;
}
