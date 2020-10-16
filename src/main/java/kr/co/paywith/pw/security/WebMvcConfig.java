package kr.co.paywith.pw.security;

import kr.co.paywith.pw.interceptor.FrontInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  FrontInterceptor frontInterceptor;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("*")
        .allowedOrigins("*");
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(frontInterceptor)
        .addPathPatterns("/api/front/**")
        .excludePathPatterns("/api/front/pw/auth");
  }
}
