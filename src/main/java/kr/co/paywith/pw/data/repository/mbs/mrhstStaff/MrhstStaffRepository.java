package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MrhstStaffRepository extends JpaRepository<MrhstStaff, Integer>, QuerydslPredicateExecutor<MrhstStaff> {

}
