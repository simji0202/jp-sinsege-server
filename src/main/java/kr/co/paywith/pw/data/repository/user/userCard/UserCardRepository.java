package kr.co.paywith.pw.data.repository.user.userCard;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserCardRepository extends JpaRepository<UserCard, Integer>,
    QuerydslPredicateExecutor<UserCard> {

  Optional<UserCard> findByCardNo(String stampNo);

}
