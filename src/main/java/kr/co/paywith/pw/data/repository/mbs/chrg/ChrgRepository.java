package kr.co.paywith.pw.data.repository.mbs.chrg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChrgRepository extends JpaRepository<Chrg, Integer>, QuerydslPredicateExecutor<Chrg> {

}