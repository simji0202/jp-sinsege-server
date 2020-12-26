package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrpayIssuRepository extends JpaRepository<PrpayIssu, Integer>, QuerydslPredicateExecutor<PrpayIssu> {

}