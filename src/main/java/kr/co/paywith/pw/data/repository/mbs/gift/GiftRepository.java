package kr.co.paywith.pw.data.repository.mbs.gift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GiftRepository extends JpaRepository<Gift, Integer>, QuerydslPredicateExecutor<Gift> {

}