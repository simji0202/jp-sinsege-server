package kr.co.paywith.pw.data.repository.mbs.cpn;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnRepository extends JpaRepository<Cpn, Integer>, QuerydslPredicateExecutor<Cpn> {
}
