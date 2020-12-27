package kr.co.paywith.pw.data.repository.mbs.pointRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PointRuleRepository extends JpaRepository<PointRule, Integer>, QuerydslPredicateExecutor<PointRule> {

}