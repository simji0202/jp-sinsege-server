package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.data.repository.ListSqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BusGuideRepository extends JpaRepository<BusGuide, Integer>, ListSqlRepository, QuerydslPredicateExecutor<BusGuide> {

}

