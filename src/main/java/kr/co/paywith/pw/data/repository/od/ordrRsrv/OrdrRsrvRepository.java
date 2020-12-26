package kr.co.paywith.pw.data.repository.od.ordrRsrv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrRsrvRepository extends JpaRepository<OrdrRsrv, Integer>, QuerydslPredicateExecutor<OrdrRsrv> {

}