package kr.co.paywith.pw.data.repository.od.dcMst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DcMstRepository extends JpaRepository<DcMst, Integer>, QuerydslPredicateExecutor<DcMst> {

}