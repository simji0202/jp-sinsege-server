package kr.co.paywith.pw.config;


import kr.co.paywith.pw.data.repository.admin.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppConfig {

  @Bean
  PasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }


  @Bean
  public ApplicationRunner applicationRunner() {
    return new ApplicationRunner() {

      @Autowired
      AdminService adminService;

      @Override
      public void run(ApplicationArguments args) throws Exception {

      }
    };
  }

  /*
   * Outh2 JDBC 설정
   */

//  @Value("${spring.datasource.url}")
//  private String datasourceUrl;
//
//  @Value("${spring.datasource.driver-class-name}")
//  private String dbDriverClassName;
//
//  @Value("${spring.datasource.username}")
//  private String dbUsername;
//
//  @Value("${spring.datasource.password}")
//  private String dbPassword;


//  @Bean
//  public DataSource dataSource() {
//    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//    dataSource.setDriverClassName(dbDriverClassName);
//    dataSource.setUrl(datasourceUrl);
//    dataSource.setUsername(dbUsername);
//    dataSource.setPassword(dbPassword);
//
//    return dataSource;
//  }
//
//  @Bean
//  public TokenStore tokenStore() {
//    return new JdbcTokenStore(dataSource());
//  }

}
