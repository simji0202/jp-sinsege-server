package kr.co.paywith.pw.data.repository.mbs.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BrandAppRepository extends JpaRepository<BrandApp, Integer>,  QuerydslPredicateExecutor<BrandApp> {

}
