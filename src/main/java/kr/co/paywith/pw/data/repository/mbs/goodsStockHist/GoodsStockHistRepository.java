package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsStockHistRepository extends JpaRepository<GoodsStockHist, Integer>, QuerydslPredicateExecutor<GoodsStockHist> {
}
