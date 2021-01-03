package kr.co.paywith.pw.data.repository.mbs.cpn;


import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnRepository extends JpaRepository<Cpn, Integer>, QuerydslPredicateExecutor<Cpn> {

  Optional<Cpn> findByCpnNo(String cpnNo);
}
