package kr.co.paywith.pw.data.repository.mbs.stat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StatRepository extends JpaRepository<Stat, Integer>, QuerydslPredicateExecutor<Stat> {

}