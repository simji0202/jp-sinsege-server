package kr.co.paywith.pw.data.repository.od.ordrOptEtcCpn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrOptEtcCpnRepository extends JpaRepository<OrdrOptEtcCpn, Integer>, QuerydslPredicateExecutor<OrdrOptEtcCpn> {

}