package kr.co.paywith.pw.security;

import kr.co.paywith.pw.data.repository.account.AccountService;
import kr.co.paywith.pw.data.repository.user.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 *  인증서버 및 리소스 서버를 위한 기본 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

//    @Bean
//    public TokenStore JdbcTokenStore(@Qualifier("dataSource") DataSource dataSource) {
//        return new JdbcTokenStore(dataSource);
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
               auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder);
    }


    /**
     * 리소스(서버) 접근 시 스프링 세쿠리리 필터 예외적 리소스 설정 ( 필터 설정 )
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Docs 문서는 세큐리티 적용 안함
        web.ignoring().mvcMatchers("/v2/api-docs", "/configuration/ui", "/docs/**", "/css/**", "/image/**",
                "/js/**", "/swagger-resources", "/swagger-resources/configuration/**","/swagger-ui.html", "/webjars/**");
        //정적 리소르를 세큐리티 적용 안함
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//          http
 //                 .cors().and()
//                  .csrf().disable()
//                  .authorizeRequests()
//                  .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//
//
//        configuration.applyPermitDefaultValues();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","OPTIONS", "HEAD"));
//        configuration.setAllowCredentials(false);
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }


}
