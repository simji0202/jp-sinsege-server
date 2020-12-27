package kr.co.paywith.pw.data.repository.mbs.questCpnGoods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuestCpnGoodsRepository extends JpaRepository<QuestCpnGoods, Integer>, QuerydslPredicateExecutor<QuestCpnGoods> {

}