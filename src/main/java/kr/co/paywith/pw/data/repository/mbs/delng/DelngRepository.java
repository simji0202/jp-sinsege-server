package kr.co.paywith.pw.data.repository.mbs.delng;


import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DelngRepository extends JpaRepository<Delng, Integer>, QuerydslPredicateExecutor<Delng> {
}
