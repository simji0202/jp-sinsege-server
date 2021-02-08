package kr.co.paywith.pw.data.repository.prx.brandPg;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BrandPgRepository extends JpaRepository<BrandPg, Integer>, QuerydslPredicateExecutor<BrandPg> {
}
