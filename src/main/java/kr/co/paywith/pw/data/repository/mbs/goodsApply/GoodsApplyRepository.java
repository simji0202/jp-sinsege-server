package kr.co.paywith.pw.data.repository.mbs.goodsApply;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsApplyRepository extends JpaRepository<GoodsApply, Integer>, QuerydslPredicateExecutor<GoodsApply> {
}
