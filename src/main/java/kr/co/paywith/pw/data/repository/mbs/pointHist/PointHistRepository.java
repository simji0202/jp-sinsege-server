package kr.co.paywith.pw.data.repository.mbs.pointHist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PointHistRepository extends JpaRepository<PointHist, Integer>, QuerydslPredicateExecutor<PointHist> {

}