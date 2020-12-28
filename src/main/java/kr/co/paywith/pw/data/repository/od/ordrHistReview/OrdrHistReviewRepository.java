package kr.co.paywith.pw.data.repository.od.ordrHistReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrHistReviewRepository extends JpaRepository<OrdrHistReview, Integer>, QuerydslPredicateExecutor<OrdrHistReview> {

}