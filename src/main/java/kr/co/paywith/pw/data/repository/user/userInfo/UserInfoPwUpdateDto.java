package kr.co.paywith.pw.data.repository.user.userInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoPwUpdateDto {

  /**
   * 웹포스 로그인 암호
   */
  private String userPw;

}
