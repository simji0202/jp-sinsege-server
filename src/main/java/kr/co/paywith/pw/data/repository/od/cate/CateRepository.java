package kr.co.paywith.pw.data.repository.od.cate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CateRepository extends JpaRepository<Cate, Integer>, QuerydslPredicateExecutor<Cate> {

}