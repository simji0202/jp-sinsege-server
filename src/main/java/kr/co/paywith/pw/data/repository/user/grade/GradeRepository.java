package kr.co.paywith.pw.data.repository.user.grade;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GradeRepository extends JpaRepository<Grade, Integer>, QuerydslPredicateExecutor<Grade> {

//  /**
//   * 최초(0), 이전(sort-1), 다음(sort+1) 등급 찾는 기능. Grade.brand가 추가되면 기능에서도 brand 조건 추가 해야 함
//   *
//   * @param sort
//   * @return
//   */
//  Optional<Grade> findBySort(Integer sort);
}
