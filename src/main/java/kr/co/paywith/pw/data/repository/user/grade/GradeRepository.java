package kr.co.paywith.pw.data.repository.user.grade;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GradeRepository extends JpaRepository<Grade, Integer>, QuerydslPredicateExecutor<Grade> {

}
