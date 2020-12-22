package kr.co.paywith.pw.data.repository.mbs.brandPg;


import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BrandPgRepository extends JpaRepository<BrandPg, Integer>, QuerydslPredicateExecutor<BrandPg> {
}
