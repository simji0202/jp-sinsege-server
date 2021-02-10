package kr.co.paywith.pw.data.repository.mbs.delngReview;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DelngReviewRepository extends JpaRepository<DelngReview, Integer>, QuerydslPredicateExecutor<DelngReview> {

  Optional<Delng> findByDelngId(Integer id);
}
