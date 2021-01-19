package kr.co.paywith.pw.data.repository.user.userDel;

import java.io.Serializable;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.Data;

/**
 * 회원 삭제 정보
 */
@Data
public class UserDelDto implements Serializable {

    /**
     * 회원 이름
     */
    private String userNm;

    /**
     * 회원 별명
     */
    private String nickNm;

    /**
     * 회원 이메일 주소
     */
    private String emailAddr;

    /**
     * 회원 휴대폰 번호
     */
    private String mobileNum;

    /**
     * 회원
     */
    private UserInfo userInfo;

}
