package kr.co.paywith.pw.data.repository.od.ordrCpn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrCpnRepository extends JpaRepository<OrdrCpn, Integer>, QuerydslPredicateExecutor<OrdrCpn> {

}