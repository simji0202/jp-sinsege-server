package kr.co.paywith.pw.data.repository.user.userDel;

import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * 회원 삭제 정보
 */
@Data
public class UserDelUpdateDto implements Serializable {

    /**
     * 회원 삭제 정보 일련번호
     */
    private Integer id;

    /**
     * 회원 아이디
     */
    private String userId;
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
    @OneToOne
    private UserInfo userInfo;

}
