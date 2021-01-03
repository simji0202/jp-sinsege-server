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
   * 변경할 암호
   */
  private String userPw;

  // kms: 암호 찾기 정책 맞춰서 필드 추가

}
