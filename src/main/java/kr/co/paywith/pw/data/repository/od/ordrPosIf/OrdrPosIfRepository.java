package kr.co.paywith.pw.data.repository.od.ordrPosIf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrPosIfRepository extends JpaRepository<OrdrPosIf, Integer>, QuerydslPredicateExecutor<OrdrPosIf> {

}