package kr.co.paywith.pw.data.repository.mbs.stamp;

import kr.co.paywith.pw.data.repository.enumeration.StampSttsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StampRepository extends JpaRepository<Stamp, Integer>, QuerydslPredicateExecutor<Stamp> {

  Iterable<Stamp> findByStampHist_Id(Integer stampHistId);

  Stamp findTopByStampHist_UserInfo_IdAndStampSttsTypeOrderByExpiredDttmAsc(
      Integer userInfoId, StampSttsType stampSttsType);

  Iterable<Stamp> findByCpnId(Integer cpnId);
}