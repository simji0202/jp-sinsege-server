package kr.co.paywith.pw.data.repository.od.goodsOrg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsOrgRepository extends JpaRepository<GoodsOrg, Integer>, QuerydslPredicateExecutor<GoodsOrg> {

}