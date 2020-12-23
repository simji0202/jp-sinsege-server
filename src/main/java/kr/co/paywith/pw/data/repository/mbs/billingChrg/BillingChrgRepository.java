package kr.co.paywith.pw.data.repository.mbs.billingChrg;


import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BillingChrgRepository extends JpaRepository<BillingChrg, Integer>, QuerydslPredicateExecutor<BillingChrg> {
}
