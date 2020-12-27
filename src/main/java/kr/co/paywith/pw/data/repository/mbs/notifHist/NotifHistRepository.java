package kr.co.paywith.pw.data.repository.mbs.notifHist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NotifHistRepository extends JpaRepository<NotifHist, Integer>, QuerydslPredicateExecutor<NotifHist> {

}