package kr.co.paywith.pw.data.repository.od.goodsOpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsOptRepository extends JpaRepository<GoodsOpt, Integer>, QuerydslPredicateExecutor<GoodsOpt> {

}