package kr.co.paywith.pw.data.repository.mbs.pointRsrvRule;

import kr.co.paywith.pw.data.repository.enumeration.PointRsrvRuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PointRsrvRuleRepository extends JpaRepository<PointRsrvRule, Integer>, QuerydslPredicateExecutor<PointRsrvRule> {

  Iterable<PointRsrvRule> findByPointRsrvRuleTypeAndStdValueGreaterThanEqualAndActiveFlIsTrue(
      PointRsrvRuleType pointRsrvRuleType, Integer amt);
}
