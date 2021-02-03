package kr.co.paywith.pw.data.repository.prx.certPhone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CertPhoneRepository extends JpaRepository<CertPhone, Integer>,
    QuerydslPredicateExecutor<CertPhone> {

  CertPhone findByMobileNumAndBrandIdAndCheckValidDttmIsNull(String mobileNum, Integer id);
}
