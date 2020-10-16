//package kr.co.paywith.pw.common;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Collections;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)	.select()
//														.apis(RequestHandlerSelectors.basePackage("kr.co.paywith.pw.controller"))
//														.paths(PathSelectors.any())
//														.build();
//	}
//
//	@SuppressWarnings("unused")
//	private ApiInfo apiInfo() {
//		return new ApiInfo("Api Information", "Description.", "Api Terms of Service", "TOS Detail", new Contact("Min-su Kang", "aaa.com", "kheeuk@gmail.com"), "License of API", "API license URL", Collections.emptyList());
//	}
//}