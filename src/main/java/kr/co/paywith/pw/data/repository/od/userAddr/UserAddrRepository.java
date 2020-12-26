package kr.co.paywith.pw.data.repository.od.userAddr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserAddrRepository extends JpaRepository<UserAddr, Integer>, QuerydslPredicateExecutor<UserAddr> {

}