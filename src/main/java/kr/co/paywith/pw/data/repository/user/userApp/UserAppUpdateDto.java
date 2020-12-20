package kr.co.paywith.pw.data.repository.user.userApp;

import kr.co.paywith.pw.data.repository.enumeration.UserAppOsCd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserAppUpdateDto {

    /**
     * 회원 앱 일련번호
     */
    private Integer id;

    /**
     * 회원 앱 구분 코드
     */
    @Enumerated(EnumType.STRING)
    private UserAppOsCd userAppOsCd;
    /**
     * 단말기 아이디(식별 고유 키)
     */
    private String trmnlId;

    /**
     * 푸시 메시지 수신 여부
     */
    private Boolean pushFl;
    /**
     * 단말기 푸시 FCM 키
     */
    private String pushKey;

    /**
     * 앱 버전 명
     */
    private String appVerNm;

    /**
     * 활성여부
     */
    private Boolean activeFl = true;

}
