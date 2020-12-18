package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MrhstTrmnlPwUpdateDto {

  /**
   * 웹포스 로그인 암호
   */
  private String userPw;

}
