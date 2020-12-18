package kr.co.paywith;

import kr.co.paywith.pw.common.AppProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@MapperScan("kr.co.paywith.pw.mapper")
public class PaywithApplication {

	@Autowired
	AppProperties appProperties;

	public static void main(String[] args) {
		SpringApplication.run(PaywithApplication.class, args);
	}


}
