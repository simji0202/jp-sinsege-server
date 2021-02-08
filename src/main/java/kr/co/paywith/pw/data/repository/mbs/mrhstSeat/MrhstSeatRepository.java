package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MrhstSeatRepository extends JpaRepository<MrhstSeat, Integer>, QuerydslPredicateExecutor<MrhstSeat> {

  int countByMrhstIdAndActiveFlIsTrue(Integer mrhstId);

  Iterable<MrhstSeat> findByMrhstIdAndActiveFlIsTrue(Integer mrhstId);
}
