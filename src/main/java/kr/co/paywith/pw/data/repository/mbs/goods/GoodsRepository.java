package kr.co.paywith.pw.data.repository.mbs.goods;


import kr.co.paywith.pw.data.repository.mbs.use.Use;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsRepository extends JpaRepository<Goods, Integer>, QuerydslPredicateExecutor<Goods> {
}
