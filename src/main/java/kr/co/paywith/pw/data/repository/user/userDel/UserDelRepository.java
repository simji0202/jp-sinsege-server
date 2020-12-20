package kr.co.paywith.pw.data.repository.user.userDel;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserDelRepository extends JpaRepository<UserDel, Integer>, QuerydslPredicateExecutor<UserDel> {

}
