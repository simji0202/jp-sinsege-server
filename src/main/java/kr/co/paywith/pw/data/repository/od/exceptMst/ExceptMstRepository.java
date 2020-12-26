package kr.co.paywith.pw.data.repository.od.exceptMst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExceptMstRepository extends JpaRepository<ExceptMst, Integer>, QuerydslPredicateExecutor<ExceptMst> {

}