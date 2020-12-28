package kr.co.paywith.pw.data.repository.od.ordrGoodsOpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrGoodsOptRepository extends JpaRepository<OrdrGoodsOpt, Integer>, QuerydslPredicateExecutor<OrdrGoodsOpt> {

}