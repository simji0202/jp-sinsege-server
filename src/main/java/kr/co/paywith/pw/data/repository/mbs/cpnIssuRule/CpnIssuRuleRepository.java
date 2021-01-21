package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;


import java.util.List;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnIssuRuleRepository extends JpaRepository<CpnIssuRule, Integer>, QuerydslPredicateExecutor<CpnIssuRule> {

  List<CpnIssuRule> findByCpnIssuRuleType(CpnIssuRuleType cpnIssuRuleType);

  List<CpnIssuRule> findByCpnIssuRuleTypeAndStdValueGreaterThanEqual(CpnIssuRuleType cpnIssuRuleType, Integer stdValue);

  int countByCpnIssuRuleType(CpnIssuRuleType cpnIssuRuleType);

  int countByCpnIssuRuleTypeAndIdNot(CpnIssuRuleType cpnIssuRuleType, Integer id);
}
