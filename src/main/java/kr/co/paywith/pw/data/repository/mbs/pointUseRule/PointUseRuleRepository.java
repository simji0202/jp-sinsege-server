package kr.co.paywith.pw.data.repository.mbs.pointUseRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PointUseRuleRepository extends JpaRepository<PointUseRule, Integer>, QuerydslPredicateExecutor<PointUseRule> {

}