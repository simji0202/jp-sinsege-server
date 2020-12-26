package kr.co.paywith.pw.data.repository.od.seatUse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SeatUseRepository extends JpaRepository<SeatUse, Integer>, QuerydslPredicateExecutor<SeatUse> {

}