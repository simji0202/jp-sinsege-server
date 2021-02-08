package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MrhstOrdrRepository extends JpaRepository<MrhstOrdr, Integer>, QuerydslPredicateExecutor<MrhstOrdr> {

}
