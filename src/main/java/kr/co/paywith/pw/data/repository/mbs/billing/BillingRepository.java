package kr.co.paywith.pw.data.repository.mbs.billing;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BillingRepository extends JpaRepository<Billing, Integer>, QuerydslPredicateExecutor<Billing> {
}
