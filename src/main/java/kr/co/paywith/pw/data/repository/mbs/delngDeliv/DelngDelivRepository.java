package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DelngDelivRepository extends JpaRepository<DelngDeliv, Integer>,
    QuerydslPredicateExecutor<DelngDeliv> {

}
