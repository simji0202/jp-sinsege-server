package kr.co.paywith.pw.data.repository.od.ordrGoodsCpn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrGoodsCpnRepository extends JpaRepository<OrdrGoodsCpn, Integer>, QuerydslPredicateExecutor<OrdrGoodsCpn> {

}