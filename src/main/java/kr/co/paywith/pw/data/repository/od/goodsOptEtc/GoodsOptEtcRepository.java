package kr.co.paywith.pw.data.repository.od.goodsOptEtc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsOptEtcRepository extends JpaRepository<GoodsOptEtc, Integer>, QuerydslPredicateExecutor<GoodsOptEtc> {

}