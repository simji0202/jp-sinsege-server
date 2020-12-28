package kr.co.paywith.pw.data.repository.od.seatSch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SeatSchRepository extends JpaRepository<SeatSch, Integer>, QuerydslPredicateExecutor<SeatSch> {

}