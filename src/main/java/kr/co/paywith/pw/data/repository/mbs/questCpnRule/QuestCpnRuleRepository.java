package kr.co.paywith.pw.data.repository.mbs.questCpnRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuestCpnRuleRepository extends JpaRepository<QuestCpnRule, Integer>, QuerydslPredicateExecutor<QuestCpnRule> {

}