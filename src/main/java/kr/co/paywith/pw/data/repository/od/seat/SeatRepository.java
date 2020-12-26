package kr.co.paywith.pw.data.repository.od.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SeatRepository extends JpaRepository<Seat, Integer>, QuerydslPredicateExecutor<Seat> {

}