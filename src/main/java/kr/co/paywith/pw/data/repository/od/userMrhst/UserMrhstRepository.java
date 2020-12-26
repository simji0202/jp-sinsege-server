package kr.co.paywith.pw.data.repository.od.userMrhst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserMrhstRepository extends JpaRepository<UserMrhst, Integer>, QuerydslPredicateExecutor<UserMrhst> {

}