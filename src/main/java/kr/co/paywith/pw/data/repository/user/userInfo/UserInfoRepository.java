package kr.co.paywith.pw.data.repository.user.userInfo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, QuerydslPredicateExecutor<UserInfo> {


  Optional<UserInfo> findByUserId(String userId);

  Optional<UserInfo> findUserInfoByUserId(String userId);

//  Optional<UserInfo> findUserInfoByStampNo(String stampNo);

}
