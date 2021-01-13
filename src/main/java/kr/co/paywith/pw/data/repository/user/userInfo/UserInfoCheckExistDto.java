package kr.co.paywith.pw.data.repository.user.userInfo;

import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import lombok.Data;


/**
 * 사용가능한 회원정보인지 확인.
 * 입력한 필드들 모두 and 조건으로 검색 후 일치하는 회원이 있는지 확인하는 용도.
 * DB save 시에도 검증을 하지만, 회원에게 사전에 가능여부를 확인해줄 때 사용
 * 일치하는 회원이 있다면 그 구성으로 회원가입이 불가하다는 걸 의미
 *
 * 사용 예>
 * userId는 전체 서버에서 중복이 불가능 하므로 요청시에 userId 확인
 * certTypeCd + certKey를 같이 입력하면 같은 인증방식으로 이미 인증한 회원이 있는지 확인. 네이버, 카카오가 같은 certKey를 줄 수 있기 때문
 *
 */
@Data
public class UserInfoCheckExistDto {

    private String userId;
    private String emailAddr;
    private String mobileNum;
    private String nickNm;
    private String certKey;
    private CertTypeCd certTypeCd;


}
