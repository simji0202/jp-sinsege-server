package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnIssuRepository extends JpaRepository<CpnIssu, Integer>, QuerydslPredicateExecutor<CpnIssu> {
}
