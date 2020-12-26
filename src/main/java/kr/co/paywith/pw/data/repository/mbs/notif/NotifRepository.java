package kr.co.paywith.pw.data.repository.mbs.notif;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NotifRepository extends JpaRepository<Notif, Integer>, QuerydslPredicateExecutor<Notif> {

}