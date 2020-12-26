package kr.co.paywith.pw.data.repository.mbs.notifUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NotifUserRepository extends JpaRepository<NotifUser, Integer>, QuerydslPredicateExecutor<NotifUser> {

}