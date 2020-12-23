package kr.co.paywith.pw.data.repository.mbs.useReq;


import kr.co.paywith.pw.data.repository.user.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UseReqRepository extends JpaRepository<UseReq, Integer>, QuerydslPredicateExecutor<UseReq> {

}
