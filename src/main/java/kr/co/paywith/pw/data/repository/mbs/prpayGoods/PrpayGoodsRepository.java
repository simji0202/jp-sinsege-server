package kr.co.paywith.pw.data.repository.mbs.prpayGoods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrpayGoodsRepository extends JpaRepository<PrpayGoods, Integer>, QuerydslPredicateExecutor<PrpayGoods> {

}