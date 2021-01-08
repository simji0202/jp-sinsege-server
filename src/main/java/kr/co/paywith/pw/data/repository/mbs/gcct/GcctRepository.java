package kr.co.paywith.pw.data.repository.mbs.gcct;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GcctRepository extends JpaRepository<Gcct, Integer>,
    QuerydslPredicateExecutor<Gcct> {

  Optional<Gcct> findByGcctNo(String gcctNo2);
}
