package kr.co.paywith.pw.data.repository.mbs.goodsStock;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsStockRepository extends JpaRepository<GoodsStock, Integer>, QuerydslPredicateExecutor<GoodsStock> {

  Optional<GoodsStock> findByGoods_IdAndMrhst_Id(Integer goodsId, Integer mrhstId);
}
