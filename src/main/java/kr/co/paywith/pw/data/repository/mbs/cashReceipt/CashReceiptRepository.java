package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface CashReceiptRepository extends CrudRepository<CashReceipt, Integer> , QuerydslPredicateExecutor<CashReceipt> {

  CashReceipt findByDelngPayment_IdAndCancelRegDttmIsNull(Integer delngPaymentId);
  Iterable<CashReceipt> findByCancelSendRsrvFlAndCancelRegDttmBefore(Boolean b, ZonedDateTime dttm);
}
