package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import javax.validation.constraints.Min;
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
public class MrhstSeatDto {

  @NameDescription("이름")
  @NotNull
  @Size(min = 1, max = 30, message = "좌석 명은 1~30자")
  private String nm;

  @NameDescription("좌석수")
  @NotNull
  @Min(value = 1, message = "최소 1 이상")
  private Integer seatCnt = 1;

  @NameDescription("매장_일련번호")
  @NotNull
  private Integer mrhstId;

  @NameDescription("사용 여부")
  private Boolean activeFl;

}
