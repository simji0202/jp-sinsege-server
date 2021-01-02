package kr.co.paywith.pw.data.repository.mbs.cd.addr3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CdAddr3Repository extends JpaRepository<CdAddr3, String>,
    QuerydslPredicateExecutor<CdAddr3> {

}
