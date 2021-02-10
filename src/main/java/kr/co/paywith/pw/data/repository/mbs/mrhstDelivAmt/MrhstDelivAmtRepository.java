package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MrhstDelivAmtRepository extends JpaRepository<MrhstDelivAmt, Integer>, QuerydslPredicateExecutor<MrhstDelivAmt> {

  Optional<MrhstDelivAmt> findByMrhstIdAndAddrCode_Cd(Integer mrhstId, String addrCodeCd);
}
