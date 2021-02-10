package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MrhstStaffDto {

  @NameDescription("이름")
  @NotNull(message = "이름을 입력해야 합니다")
  @Size(min = 0, max = 30, message = "이름은 1~30자로 입력해야 합니다")
  private String nm;

  @NameDescription("매장_일련번호")
  @NotNull(message = "매장 정보를 입력해야 합니다")
  private int mrhstId;

  @NameDescription("사용 여부")
  private Boolean activeFl;

}
