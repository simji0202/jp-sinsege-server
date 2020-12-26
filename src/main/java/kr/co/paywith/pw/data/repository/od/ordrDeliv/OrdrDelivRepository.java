package kr.co.paywith.pw.data.repository.od.ordrDeliv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrDelivRepository extends JpaRepository<OrdrDeliv, Integer>, QuerydslPredicateExecutor<OrdrDeliv> {

}