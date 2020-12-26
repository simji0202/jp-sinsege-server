package kr.co.paywith.pw.data.repository.od.mrhstoff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MrhstoffRepository extends JpaRepository<Mrhstoff, Integer>, QuerydslPredicateExecutor<Mrhstoff> {

}