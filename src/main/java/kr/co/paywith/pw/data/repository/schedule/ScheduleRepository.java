package kr.co.paywith.pw.data.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, QuerydslPredicateExecutor<Schedule> {

}