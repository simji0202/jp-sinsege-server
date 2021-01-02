package kr.co.paywith.pw.data.repository.mbs.cpn;

import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoDto;
import lombok.Data;

/**
 * 가맹점
 */
@Data
public class CpnDto {


    /**
     * 쿠폰 소지 회원
     */
    private UserInfoDto userInfoDto;

}
