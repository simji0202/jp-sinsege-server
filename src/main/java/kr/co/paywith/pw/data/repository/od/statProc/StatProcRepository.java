package kr.co.paywith.pw.data.repository.od.statProc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StatProcRepository extends JpaRepository<StatProc, Integer>, QuerydslPredicateExecutor<StatProc> {

}