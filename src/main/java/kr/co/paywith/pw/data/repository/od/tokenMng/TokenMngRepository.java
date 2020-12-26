package kr.co.paywith.pw.data.repository.od.tokenMng;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TokenMngRepository extends JpaRepository<TokenMng, Integer>, QuerydslPredicateExecutor<TokenMng> {

}