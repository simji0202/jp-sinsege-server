package kr.co.paywith.pw.data.repository.agents;

import java.util.Optional;
import kr.co.paywith.pw.data.repository.partners.Partners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AgentsRepository extends JpaRepository<Agents, Integer>,
        QuerydslPredicateExecutor<Agents> {
  @Query(value = "select p from Agents p where p.adminId= :adminId")
  Optional<Agents> findByAdminId(String adminId);
}
