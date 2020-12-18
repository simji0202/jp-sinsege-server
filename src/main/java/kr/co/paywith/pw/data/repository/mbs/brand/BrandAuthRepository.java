package kr.co.paywith.pw.data.repository.mbs.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BrandAuthRepository extends JpaRepository<BrandAuth, Integer>,  QuerydslPredicateExecutor<BrandAuth> {

}
