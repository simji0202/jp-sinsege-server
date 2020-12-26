package kr.co.paywith.pw.data.repository.od.ordrComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrCommentRepository extends JpaRepository<OrdrComment, Integer>, QuerydslPredicateExecutor<OrdrComment> {

}