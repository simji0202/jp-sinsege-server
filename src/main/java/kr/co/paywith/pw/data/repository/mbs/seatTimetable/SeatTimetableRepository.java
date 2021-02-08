package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SeatTimetableRepository extends JpaRepository<SeatTimetable, Integer>, QuerydslPredicateExecutor<SeatTimetable> {

  int countByStartDttmBetween(LocalDateTime s1, LocalDateTime s2);
}
