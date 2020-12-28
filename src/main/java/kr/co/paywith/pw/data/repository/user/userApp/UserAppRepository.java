package kr.co.paywith.pw.data.repository.user.userApp;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserAppRepository extends JpaRepository<UserApp, Integer>, QuerydslPredicateExecutor<UserApp> {

   Optional<UserApp> findByUserInfo_IdAndTrmnlId(Integer userId, String trmnlId);
}
