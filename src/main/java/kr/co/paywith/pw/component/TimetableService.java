package kr.co.paywith.pw.component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstRepository;
import kr.co.paywith.pw.data.repository.mbs.mrhstSeat.MrhstSeat;
import kr.co.paywith.pw.data.repository.mbs.mrhstSeat.MrhstSeatRepository;
import kr.co.paywith.pw.data.repository.mbs.seatTimetable.SeatTimetable;
import kr.co.paywith.pw.data.repository.mbs.seatTimetable.SeatTimetableRepository;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TimetableService {

  /**
   * D+1일 부터 D+n 일까지 시간표 생성
   */
  final int DAY = 14;

  @Autowired
  private SeatTimetableRepository seatTimetableRepository;

  @Autowired
  private MrhstRepository mrhstRepository;

  @Autowired
  private MrhstSeatRepository mrhstSeatRepository;

  /**
   * 매장별 시트별 시간표 생성
   */
  @Scheduled(cron = "0 4 * * * *")
  @SchedulerLock(name = "makeTimetable", lockAtMostFor = "50m", lockAtLeastFor = "3m")
  @Transactional
  public void makeTimetable() {
    for (Mrhst mrhst: mrhstRepository.findByMrhstOrdr_UseTimetableFlIsTrue()) {
      // 매장 별 timetable 생성
      for (int i = 0; i < DAY; i++) {
        LocalDateTime startDttm = LocalDateTime.now().plusDays(i+1).withHour(0).withMinute(0).withSecond(0);
        int prevCnt =
            seatTimetableRepository.countByStartDttmBetween(startDttm, startDttm.plusDays(1).minusSeconds(1));
        if (prevCnt == 0) {
          // 생성된 timetable 이 없으므로 새로 생성

          LocalTime startTm, endTm; // 기준 일 요일에 설정한 시작 시간
          switch (startDttm.getDayOfWeek()) {
            case MONDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm1();
              endTm = mrhst.getMrhstOrdr().getCloseTm1();
              break;
            case TUESDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm2();
              endTm = mrhst.getMrhstOrdr().getCloseTm2();
              break;
            case WEDNESDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm3();
              endTm = mrhst.getMrhstOrdr().getCloseTm3();
              break;
            case THURSDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm4();
              endTm = mrhst.getMrhstOrdr().getCloseTm4();
              break;
            case FRIDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm5();
              endTm = mrhst.getMrhstOrdr().getCloseTm5();
              break;
            case SATURDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm6();
              endTm = mrhst.getMrhstOrdr().getCloseTm6();
              break;
            case SUNDAY:
              startTm = mrhst.getMrhstOrdr().getOpenTm7();
              endTm = mrhst.getMrhstOrdr().getCloseTm7();
              break;
            default:
              startTm = null;
              endTm = null;
              break;
          }
          if (startTm != null && endTm != null) {
            // 시간 설정했으면 시간표 생성
            // 테이블 최초 일시
            LocalDateTime tableStartDttm = startDttm.withHour(startTm.getHour()).withMinute(startTm.getMinute());
            LocalDateTime tableEndDttm = startDttm.withHour(endTm.getHour()).withMinute(endTm.getMinute());
              if (mrhstSeatRepository.countByMrhstIdAndActiveFlIsTrue(mrhst.getId()) > 0) {
                // 시트별 생성
                for (MrhstSeat mrhstSeat :
                    mrhstSeatRepository.findByMrhstIdAndActiveFlIsTrue(mrhst.getId())) {
                  do {
                    SeatTimetable seatTimetable = new SeatTimetable();
                    seatTimetable.setMrhstSeat(mrhstSeat);
                    seatTimetable.setStartDttm(tableStartDttm);
                    seatTimetable.setEndDttm(tableStartDttm.plusMinutes(mrhst.getMrhstOrdr().getRsrvUnitMin()).minusSeconds(1));
                    seatTimetableRepository.save(seatTimetable);
                    tableStartDttm = tableStartDttm.plusMinutes(mrhst.getMrhstOrdr().getRsrvUnitMin());
                  } while (tableStartDttm.isBefore(tableEndDttm));
                }
              } else {
                // 시트 없는 시간표만 생성
//                do {
//                  SeatTimetable seatTimetable = new SeatTimetable();
//                  seatTimetable.setMrhstSeat(null);
//                  seatTimetable.setStartDttm(tableStartDttm);
//                  seatTimetable.setEndDttm(tableStartDttm.plusMinutes(mrhst.getMrhstOrdr().getRsrvUnitMin()).minusSeconds(1));
//                  seatTimetableRepository.save(seatTimetable);
//                  tableStartDttm = tableStartDttm.plusMinutes(mrhst.getMrhstOrdr().getRsrvUnitMin());
//                } while (tableStartDttm.isBefore(tableEndDttm));
              }
          }
        }
      }
    }
  }
}
