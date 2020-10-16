package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.data.repository.ListSqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer>, ListSqlRepository,
        QuerydslPredicateExecutor<Company> {

}

