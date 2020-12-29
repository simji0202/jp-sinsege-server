package kr.co.paywith.pw.data.repository.user.userStamp;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserStampRepository extends JpaRepository<UserStamp, Integer>,
    QuerydslPredicateExecutor<UserStamp> {

  Optional<UserStamp> findByStampNo(String stampNo);

}