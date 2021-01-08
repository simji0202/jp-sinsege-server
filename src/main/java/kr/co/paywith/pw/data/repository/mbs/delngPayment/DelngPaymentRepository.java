package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DelngPaymentRepository extends JpaRepository<DelngPayment, Integer>, QuerydslPredicateExecutor<DelngPayment> {

}