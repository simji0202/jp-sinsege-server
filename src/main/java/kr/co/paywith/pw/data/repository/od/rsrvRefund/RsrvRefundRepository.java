package kr.co.paywith.pw.data.repository.od.rsrvRefund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RsrvRefundRepository extends JpaRepository<RsrvRefund, Integer>, QuerydslPredicateExecutor<RsrvRefund> {

}