package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import java.util.List;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.seatTimetable.SeatTimetable;
import lombok.Data;

@Data
public class DelngOrdrSeatTimetableUpdateDto {

  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("좌석 시간표 목록")
  private List<SeatTimetable> seatTimetableList;

  @NameDescription("직원_일련번호")
  private Integer staffId;


}
