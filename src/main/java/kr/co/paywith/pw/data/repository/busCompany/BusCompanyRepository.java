package kr.co.paywith.pw.data.repository.busCompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BusCompanyRepository extends JpaRepository<BusCompany, Integer>, QuerydslPredicateExecutor<BusCompany> {

}