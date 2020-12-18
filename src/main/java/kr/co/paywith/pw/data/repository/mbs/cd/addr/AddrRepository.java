package kr.co.paywith.pw.data.repository.mbs.cd.addr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddrRepository extends JpaRepository<Addr, String>,
    QuerydslPredicateExecutor<Addr> {

}
