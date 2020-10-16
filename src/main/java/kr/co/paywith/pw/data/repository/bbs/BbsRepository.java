package kr.co.paywith.pw.data.repository.bbs;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BbsRepository extends JpaRepository<Bbs, Integer>,  QuerydslPredicateExecutor<Bbs>, JpaSpecificationExecutor<Bbs> {

}
