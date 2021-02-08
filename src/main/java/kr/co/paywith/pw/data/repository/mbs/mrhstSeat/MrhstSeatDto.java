package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

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
  private String nm;

  @NameDescription("좌석수")
  private Integer seatCnt = 1;

  @NameDescription("매장_일련번호")
  private Integer mrhstId;

  @NameDescription("사용 여부")
  private Boolean activeFl;

}
