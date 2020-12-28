package kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrGoodsOptEtcRepository extends JpaRepository<OrdrGoodsOptEtc, Integer>, QuerydslPredicateExecutor<OrdrGoodsOptEtc> {

}