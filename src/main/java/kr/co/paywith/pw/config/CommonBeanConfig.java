package kr.co.paywith.pw.config;

import com.popbill.api.MessageService;
import com.popbill.api.message.MessageServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeanConfig {

  /**
   * 팝빌 문자 서비스
   */
  @Bean
  public MessageService messageService() {
    MessageService messageService = new MessageServiceImp();
    ((MessageServiceImp) messageService).setLinkID("PAYWITH");
    ((MessageServiceImp) messageService)
        .setSecretKey("CImNNoBEWFpJZ3nmIHYbV7/ToFOxnH8UADgyw9KYx0w=");
    ((MessageServiceImp) messageService).setTest(false);
    ((MessageServiceImp) messageService).setIPRestrictOnOff(true);

    return messageService;
  }
}
