package kr.co.paywith.pw.data.repository.mbs.cm;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CpnMasterRepository extends JpaRepository<CpnMaster, Integer>, QuerydslPredicateExecutor<CpnMaster> {
}
