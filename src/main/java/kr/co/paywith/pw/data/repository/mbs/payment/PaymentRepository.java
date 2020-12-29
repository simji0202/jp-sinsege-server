package kr.co.paywith.pw.data.repository.mbs.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, QuerydslPredicateExecutor<Payment> {

}