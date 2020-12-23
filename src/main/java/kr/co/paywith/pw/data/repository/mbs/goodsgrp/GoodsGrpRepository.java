package kr.co.paywith.pw.data.repository.mbs.goodsgrp;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsGrpRepository extends JpaRepository<GoodsGrp, Integer>, QuerydslPredicateExecutor<GoodsGrp> {
}
