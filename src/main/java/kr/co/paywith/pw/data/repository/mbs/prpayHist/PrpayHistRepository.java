package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrpayHistRepository extends JpaRepository<PrpayHist, Integer>, QuerydslPredicateExecutor<PrpayHist> {

}