package kr.co.paywith.pw.data.repository.mbs.pointRule;

import kr.co.paywith.pw.data.repository.enumeration.PointRuleTypeCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PointRuleRepository extends JpaRepository<PointRule, Integer>, QuerydslPredicateExecutor<PointRule> {

  Iterable<PointRule> findByPointRuleTypeCdAndMinAmtGreaterThanEqualAndActiveFlIsTrue(
      PointRuleTypeCd pointRuleTypeCd, Integer amt);
}
