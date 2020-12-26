package kr.co.paywith.pw.data.repository.mbs.refund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RefundRepository extends JpaRepository<Refund, Integer>, QuerydslPredicateExecutor<Refund> {

}