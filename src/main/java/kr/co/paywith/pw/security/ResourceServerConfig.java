package kr.co.paywith.pw.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    public static final String RESOURCE_ID = "resource_id";


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .anonymous()
               .and()
            .authorizeRequests()

                // 인증없이 리소스 참조 API
                .mvcMatchers("/api/front/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/userInfo/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/admin/idchk").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/bbs").permitAll()
                .mvcMatchers(" /swagger-resources/**").permitAll()

                .anyRequest().authenticated()
                .and()
            .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
