package kr.co.paywith.pw;

import com.popbill.api.KakaoService;
import com.popbill.api.MessageService;
import com.popbill.api.kakao.KakaoServiceImp;
import com.popbill.api.message.MessageServiceImp;
import kr.co.paywith.pw.common.AppProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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

	@Bean
	public MessageService popbillMessageService() {
		MessageService popbillMessageService = new MessageServiceImp();
		((MessageServiceImp) popbillMessageService).setLinkID(appProperties.getLinkID());
		((MessageServiceImp) popbillMessageService).setSecretKey(appProperties.getSecretKey());
		((MessageServiceImp) popbillMessageService)
				.setTest(appProperties.getIsTest() == null ? true : appProperties.getIsTest());
		((MessageServiceImp) popbillMessageService).setIPRestrictOnOff(true);

		return popbillMessageService;
	}

	@Bean
	public KakaoService popbillKakaoService() {
		KakaoService popbillKakaoService = new KakaoServiceImp();
		((KakaoServiceImp) popbillKakaoService).setLinkID(appProperties.getLinkID());
		((KakaoServiceImp) popbillKakaoService).setSecretKey(appProperties.getSecretKey());
		((KakaoServiceImp) popbillKakaoService)
				.setTest(appProperties.getIsTest() == null ? true : appProperties.getIsTest());
		((KakaoServiceImp) popbillKakaoService).setIPRestrictOnOff(true);

		return popbillKakaoService;
	}

	@Bean
	public TaskScheduler taskScheduler() {

		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);

		return taskScheduler;
	}

}
