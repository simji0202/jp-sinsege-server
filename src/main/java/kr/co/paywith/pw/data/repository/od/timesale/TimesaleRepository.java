package kr.co.paywith.pw.data.repository.od.timesale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TimesaleRepository extends JpaRepository<Timesale, Integer>, QuerydslPredicateExecutor<Timesale> {

}