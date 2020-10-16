package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.data.repository.ListSqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PartnersRepository extends JpaRepository<Partners, Integer>, ListSqlRepository,
        QuerydslPredicateExecutor<Partners> {

//  @Query(value="select * from partners_move_service  where id = :id",nativeQuery=true)
//  List<PartnersMoveService> findPartnersMoveServicesById  (@Param("id") Integer id);

  @Query(value = "select p from Partners p where p.adminId= :adminId")
  Optional<Partners> findByAdminId(String adminId);


  @Query(value = "select p from Partners p where p.name= :name")
  Optional<Partners> findByName(String name);


  @Modifying
  @Transactional
  @Query("UPDATE Partners p set p.name = :name  where p.id = :id")
  void updatePartnersName(@Param("id") Integer id, String name);
}
