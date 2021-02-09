package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import java.util.List;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.seatTimetable.SeatTimetable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelngOrdrSeatTimetableDto {

  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("좌석 시간표 목록")
  private List<SeatTimetable> seatTimetableList;

  @NameDescription("직원_일련번호")
  private Integer staffId;

}
