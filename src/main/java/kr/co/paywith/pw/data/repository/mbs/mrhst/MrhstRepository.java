package kr.co.paywith.pw.data.repository.mbs.mrhst;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MrhstRepository extends JpaRepository<Mrhst, Integer>, QuerydslPredicateExecutor<Mrhst> {
}
