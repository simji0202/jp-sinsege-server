package kr.co.paywith.pw.data.repository.mbs.msgRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MsgRuleRepository extends JpaRepository<MsgRule, Integer>, QuerydslPredicateExecutor<MsgRule> {

}