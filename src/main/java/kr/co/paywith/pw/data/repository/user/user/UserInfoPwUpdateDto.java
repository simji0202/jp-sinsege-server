package kr.co.paywith.pw.data.repository.user.user;

import lombok.Data;

@Data
public class UserInfoPwUpdateDto {

  /**
   * 변경할 암호
   */
  private String userPw;

  // kms: 암호 찾기 정책 맞춰서 필드 추가

}
