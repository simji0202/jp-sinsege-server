package kr.co.paywith.pw.data.repository.od.ordrCommentImg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdrCommentImgRepository extends JpaRepository<OrdrCommentImg, Integer>, QuerydslPredicateExecutor<OrdrCommentImg> {

}