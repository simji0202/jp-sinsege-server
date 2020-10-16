package kr.co.paywith.pw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfig {


  @Bean
  public Docket api() {

    Contact contact = new Contact(" 주식회사 페이위드"
        , " www.paywith.co.kr"
        , "che@paywith.co.kr");

    ApiInfo info = new ApiInfo(" 유투어버스 (크루즈)  API "
        , "이 문서는 크루즈 예약 관리 플렛폼 시스템의 API 문서를 설명한다  ."
        , "V1"
        , "TOS Detail"
        , contact
        , "License of API"
        , "API license URL"
        , Collections.emptyList());

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .apiInfo(info)

        ;
  }


}
