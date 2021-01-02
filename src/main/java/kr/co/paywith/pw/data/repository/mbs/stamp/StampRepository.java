package kr.co.paywith.pw.data.repository.mbs.stamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StampRepository extends JpaRepository<Stamp, Integer>, QuerydslPredicateExecutor<Stamp> {

  Iterable<Stamp> findByStampHist_Id(Integer stampHistId);
}