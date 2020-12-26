package kr.co.paywith.pw.data.repository.od.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StaffRepository extends JpaRepository<Staff, Integer>, QuerydslPredicateExecutor<Staff> {

}