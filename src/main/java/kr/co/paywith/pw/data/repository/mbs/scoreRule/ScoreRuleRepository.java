package kr.co.paywith.pw.data.repository.mbs.scoreRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ScoreRuleRepository extends JpaRepository<ScoreRule, Integer>, QuerydslPredicateExecutor<ScoreRule> {

}