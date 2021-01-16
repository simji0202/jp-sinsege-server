package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ScoreHistRepository extends JpaRepository<ScoreHist, Integer>, QuerydslPredicateExecutor<ScoreHist> {

  ScoreHist findByDelng_Id(Integer delngId);
}
