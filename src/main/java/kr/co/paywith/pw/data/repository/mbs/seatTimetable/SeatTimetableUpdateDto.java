package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import java.time.LocalDateTime;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.SeatSttsType;
import kr.co.paywith.pw.data.repository.mbs.mrhstSeat.MrhstSeat;
import kr.co.paywith.pw.data.repository.mbs.mrhstStaff.MrhstStaff;
import lombok.Data;

@Data
public class SeatTimetableUpdateDto {

  @NameDescription("좌석 상태")
  private SeatSttsType seatSttsType;
}
