package kr.co.paywith.pw.data.repository.od.ordrPay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrPayRepository extends JpaRepository<OrdrPay, Integer>, QuerydslPredicateExecutor<OrdrPay> {

}