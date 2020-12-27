package kr.co.paywith.pw.data.repository.mbs.prpay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrpayRepository extends JpaRepository<Prpay, Integer>, QuerydslPredicateExecutor<Prpay> {

}