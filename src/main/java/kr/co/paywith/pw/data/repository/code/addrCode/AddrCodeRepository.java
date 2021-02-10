package kr.co.paywith.pw.data.repository.code.addrCode;

import kr.co.paywith.pw.data.repository.mbs.bbs.Bbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddrCodeRepository extends JpaRepository <AddrCode, String>, QuerydslPredicateExecutor<AddrCode> {
}
