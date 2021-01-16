package kr.co.paywith;

import java.util.Optional;
import java.util.Set;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.AuthCd;
import kr.co.paywith.pw.data.repository.enumeration.DtTypeCd;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandRepository;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandSetting;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@MapperScan("kr.co.paywith.pw.mapper")
@Slf4j
public class PaywithApplication implements ApplicationRunner {

  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private BrandRepository brandRepository;

  public static void main(String[] args) {
    SpringApplication.run(PaywithApplication.class, args);

    BCryptPasswordEncoder bcr = new BCryptPasswordEncoder();
//    System.out.println("1234 암호 === " + bcr.encode("1234"));
//    System.out.println("paywith 암호 === " + bcr.encode("paywith"));
    log.info("서비스 정상 시작 완료");

  }

  @Override
  public void run(ApplicationArguments args) {
    BCryptPasswordEncoder bcr = new BCryptPasswordEncoder();
    if ("local".equals(activeProfile)) {
      Optional<Admin> adminOptional = adminRepository.findByAdminId("admin");
      Admin admin = adminOptional.orElse(null);
      if (admin == null) {
        admin = new Admin();

      }
      admin.setAdminId("admin");
      admin.setAdminPw(bcr.encode("1234"));
      admin.setAdminNm("관리자");
      admin.setAuthCd(AuthCd.MST);
      admin.setRoles(Set.of(AdminRole.ADMIN_MASTER));
      adminRepository.save(admin);

        Optional<Brand> brandOptional = brandRepository.findById(1);
        Brand brand = brandOptional.orElse(null);
        if (brand == null) {
          brand = new Brand();
        }
        brand.setBrandCd("");
        brand.setBrandNm("-");

        BrandSetting brandSetting = new BrandSetting();
        brandSetting.setMinUsePointAmt(1000);
        brandSetting.setStampMaxCnt(10);
        brandSetting.setPrpayValidPeriod(5);
        brandSetting.setPrpayValidPeriodCd(DtTypeCd.Y);
        brandSetting.setStampValidPeriod(180);
     //   brandSetting.setStampValidPeriodCd(DtTypeCd.D);

        brandRepository.save(brand);
    }
  }
}
