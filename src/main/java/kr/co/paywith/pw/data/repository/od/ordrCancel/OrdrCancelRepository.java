package kr.co.paywith.pw.data.repository.od.ordrCancel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrCancelRepository extends JpaRepository<OrdrCancel, Integer>, QuerydslPredicateExecutor<OrdrCancel> {

}