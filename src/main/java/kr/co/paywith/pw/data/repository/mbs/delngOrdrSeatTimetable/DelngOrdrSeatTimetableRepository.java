package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DelngOrdrSeatTimetableRepository extends JpaRepository<DelngOrdrSeatTimetable, Integer>, QuerydslPredicateExecutor<DelngOrdrSeatTimetable> {

}
