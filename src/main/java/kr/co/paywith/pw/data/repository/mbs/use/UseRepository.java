package kr.co.paywith.pw.data.repository.mbs.use;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UseRepository extends JpaRepository<Use, Integer>, QuerydslPredicateExecutor<Use> {
}
