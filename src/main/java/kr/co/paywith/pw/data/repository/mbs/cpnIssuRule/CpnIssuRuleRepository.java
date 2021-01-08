package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnIssuRuleRepository extends JpaRepository<CpnIssuRule, Integer>, QuerydslPredicateExecutor<CpnIssuRule> {
}
