package kr.co.paywith.pw.data.repository.user.userApp;

import kr.co.paywith.pw.data.repository.enumeration.UserAppOsCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserAppDto {

    /**
     * 회원 앱 구분 코드
     */
    private UserAppOsCd userAppOsCd;

    /**
     * 단말기 아이디(식별 고유 키)
     */
    private String trmnlId;

    /**
     * 푸시 메시지 수신 여부
     */
    private Boolean pushFl = true;

    /**
     * 단말기 푸시 FCM 키
     */
    private String pushKey;

    /**
     * 앱 버전 명
     */
    private String appVerNm;

    private UserInfoDto userInfo;

}
