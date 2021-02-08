package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import java.time.LocalDateTime;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.SeatSttsType;
import kr.co.paywith.pw.data.repository.mbs.mrhstSeat.MrhstSeat;
import kr.co.paywith.pw.data.repository.mbs.mrhstStaff.MrhstStaff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatTimetableDto {

  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("시작시각")
  private LocalDateTime startDttm;

  @NameDescription("종료시각")
  private LocalDateTime endDttm;

  @NameDescription("좌석")
  private MrhstSeat mrhstSeat;

  @NameDescription("직원")
  private MrhstStaff mrhstStaff;

//	@NameDescription("좌석_일련번호")
//	private int  seatId;
//
//	@NameDescription("직원_일련번호")
//	private int  staffId;

  @NameDescription("좌석 상태")
  private SeatSttsType seatSttsType;


}
