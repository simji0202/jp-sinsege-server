package kr.co.paywith.pw.data.repository.mbs.goodsOptMaster;

import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsOptMasterRepository extends JpaRepository<GoodsOptMaster, Integer>, QuerydslPredicateExecutor<GoodsOptMaster> {

}