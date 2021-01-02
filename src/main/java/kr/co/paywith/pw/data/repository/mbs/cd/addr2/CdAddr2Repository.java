package kr.co.paywith.pw.data.repository.mbs.cd.addr2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CdAddr2Repository extends JpaRepository<CdAddr2, String>,
    QuerydslPredicateExecutor<CdAddr2> {

}
