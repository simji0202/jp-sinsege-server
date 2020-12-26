package kr.co.paywith.pw.data.repository.mbs.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileRepository extends JpaRepository<File, Integer>, QuerydslPredicateExecutor<File> {

}