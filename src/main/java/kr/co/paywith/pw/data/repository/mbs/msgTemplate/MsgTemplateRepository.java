package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MsgTemplateRepository extends JpaRepository<MsgTemplate, Integer>, QuerydslPredicateExecutor<MsgTemplate> {

}