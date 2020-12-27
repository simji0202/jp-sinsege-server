package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NotifMrhstRepository extends JpaRepository<NotifMrhst, Integer>, QuerydslPredicateExecutor<NotifMrhst> {

}