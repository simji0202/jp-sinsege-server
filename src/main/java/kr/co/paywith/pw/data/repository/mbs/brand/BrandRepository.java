package kr.co.paywith.pw.data.repository.mbs.brand;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BrandRepository extends JpaRepository<Brand, Integer>,  QuerydslPredicateExecutor<Brand> {

  Optional<Brand> findByBrandCd(String brandCd);

  Optional<Brand> findByBrandCdAndIdNot(String brandCd, Integer id);

  Optional<Brand> findByBrandCdAndBrandSetting_CorpNo(String brandCd, String corpNo);
}
