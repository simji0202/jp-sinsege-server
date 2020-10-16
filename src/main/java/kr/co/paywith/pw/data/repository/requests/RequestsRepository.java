package kr.co.paywith.pw.data.repository.requests;

import kr.co.paywith.pw.data.repository.ListSqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RequestsRepository extends JpaRepository<Requests, Integer>, ListSqlRepository, QuerydslPredicateExecutor<Requests> {


  @Modifying
  @Transactional
  @Query("UPDATE Requests r set r.reqStateCd = :status  where r.id = :id")
  void updateRequestsStatus(@Param("id") Integer id, RequestStatusEnum status);



}
