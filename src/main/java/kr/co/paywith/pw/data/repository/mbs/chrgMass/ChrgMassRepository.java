package kr.co.paywith.pw.data.repository.mbs.chrgMass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChrgMassRepository extends JpaRepository<ChrgMass, Integer>, QuerydslPredicateExecutor<ChrgMass> {

}