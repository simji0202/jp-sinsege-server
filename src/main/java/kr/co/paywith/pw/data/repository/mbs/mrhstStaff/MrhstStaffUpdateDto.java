package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class MrhstStaffUpdateDto {

  @NameDescription("이름")
  private String nm;

  @NameDescription("사용 여부")
  private Boolean activeFl;

}
