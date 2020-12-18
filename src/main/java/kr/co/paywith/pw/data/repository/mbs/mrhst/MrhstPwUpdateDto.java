package kr.co.paywith.pw.data.repository.mbs.mrhst;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MrhstPwUpdateDto {

  @NameDescription("비밀번호")
  private String mrhstPw;

}
