package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MrhstTrmnlRepository extends JpaRepository<MrhstTrmnl, Integer>, QuerydslPredicateExecutor<MrhstTrmnl> {

    //
    Optional<MrhstTrmnl> findByUserId(String mrhstTrmnlId);

}

