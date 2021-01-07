package kr.co.paywith.pw.data.repository.user.grade;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface GradeUpRepository extends JpaRepository<GradeUp, Integer>, QuerydslPredicateExecutor<GradeUp> {

}
