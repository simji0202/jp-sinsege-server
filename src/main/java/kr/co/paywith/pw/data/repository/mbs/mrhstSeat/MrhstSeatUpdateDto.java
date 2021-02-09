package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class MrhstSeatUpdateDto {

  @NameDescription("이름")
  private String nm;

  @NameDescription("좌석수")
  private int seatCnt;

  @NameDescription("사용 여부")
  private Boolean activeFl;


}
