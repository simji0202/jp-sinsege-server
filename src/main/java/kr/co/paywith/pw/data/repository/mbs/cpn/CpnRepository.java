package kr.co.paywith.pw.data.repository.mbs.cpn;


import java.util.Optional;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnRepository extends JpaRepository<Cpn, Integer>, QuerydslPredicateExecutor<Cpn> {

  Optional<Cpn> findByCpnNo(String cpnNo);

  Iterable<Cpn> findByUserInfo_IdAndCpnSttsTypeAndCpnIssu_CpnIssuRule_CpnIssuRuleTypeOrderByRegDttm(
      Integer userInfoId,
      CpnSttsType cpnSttsTypeCd, CpnIssuRuleType cpnIssuRuleType);
}
