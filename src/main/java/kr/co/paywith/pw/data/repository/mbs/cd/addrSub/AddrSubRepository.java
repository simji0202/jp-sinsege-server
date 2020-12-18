package kr.co.paywith.pw.data.repository.mbs.cd.addrSub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddrSubRepository extends JpaRepository<AddrSub, String>,
    QuerydslPredicateExecutor<AddrSub> {

}
