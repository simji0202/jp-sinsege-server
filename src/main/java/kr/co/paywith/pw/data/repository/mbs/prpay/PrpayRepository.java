package kr.co.paywith.pw.data.repository.mbs.prpay;

import java.util.Optional;
import kr.co.paywith.pw.data.repository.user.userStamp.UserStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrpayRepository extends JpaRepository<Prpay, Integer>, QuerydslPredicateExecutor<Prpay> {
  Optional<Prpay> findByPrpayNo(String prpayNo);
}