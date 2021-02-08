package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DelngOrdrRepository extends JpaRepository<DelngOrdr, Integer>, QuerydslPredicateExecutor<DelngOrdr> {

}
