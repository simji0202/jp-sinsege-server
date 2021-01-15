package kr.co.paywith.pw.data.repository.mbs.stampHist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StampHistRepository extends JpaRepository<StampHist, Integer>, QuerydslPredicateExecutor<StampHist> {

  StampHist findByDelng_Id(Integer id);
}
