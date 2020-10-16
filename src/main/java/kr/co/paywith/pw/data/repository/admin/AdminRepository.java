package kr.co.paywith.pw.data.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>,
        QuerydslPredicateExecutor<Admin> {

  Optional<Admin> findByAdminId(String adminId);

  Optional<AdminInfo> findAdminByAdminId(String adminId);

//  @Transactional
//  @Modifying
//  @Query(value="update Admin a set a.adminPw=:adminPw where a.id=:id", nativeQuery=true)
//  void updateUserEmailVerificationStatus(@Param("adminPw") String adminPw ,
//                                           @Param("id") String userId);

  @Query("select admin from Admin admin where admin.adminId =:adminId")
  Admin findUserEntityByUserId(@Param("adminId") String adminId);

}
