package kr.co.paywith.pw.security;

import javax.sql.DataSource;
import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.data.repository.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig  extends AuthorizationServerConfigurerAdapter  {





    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminService adminService;

    @Autowired
    TokenStore tokenStore;

//    @Autowired
//     TokenStore JdbcTokenStore;

    //   private final DataSource dataSource;


    @Autowired
    AppProperties appProperties;


//    private final AuthenticationManager authenticationManager;
//    private final AppConfig appConfig;
//    @Autowired
//    public AuthServerConfig(AuthenticationManager authenticationManager, AppConfig appConfig) {
//        this.authenticationManager = authenticationManager;
//        this.appConfig = appConfig;
//    }


//    @Bean
//    public TokenStore tokenStore() { //(2)
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Bean
//    public ApprovalStore approvalStore() { //(3)
//        return new JdbcApprovalStore(dataSource);
//    }





    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
        //        .checkTokenAccess("isAuthenticated()")
        //        .allowFormAuthenticationForClients()
        ;

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 데이타 베이스에서 관리 (장애 발생을 고려 할 경우 가장 베스트한 방법)
        // 차후 구현 예정

        // inMemory 에서 관리 방법

        String clientId = "sinsege";
        String clientSecret = "paywith1234";

        System.out.println("########### clientId ##########" + appProperties.getClientId());
        System.out.println("########### clientSecret ##########" + appProperties.getClientSecret());



//        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
//
        clients.inMemory()
                .withClient(clientId)
                // refresh_token 발행
                .authorizedGrantTypes("password", "refresh_token")
                // 임의값 설정
                .scopes("read", "write")
                //
                .secret(this.passwordEncoder.encode(clientSecret))
//                // accessToken 1일
                .accessTokenValiditySeconds(10*60*6*24)
                // accessToken 10분
     //           .accessTokenValiditySeconds(10*60)
                // refreshToken 30일
                .refreshTokenValiditySeconds(10*60*6*24*30)
        ;


    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(adminService)
//          //   .tokenStore(tokenStore)
//           //     .tokenStore(JdbcTokenStore)


//        endpoints. approvalStore(approvalStore())
//            .tokenStore(tokenStore())
//        .authenticationManager(authenticationManager)
        //   .tokenStore(tokenStore)
        //     .tokenStore(JdbcTokenStore)

        ;


        // inMemory 에서 관리 방법
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(adminService)
                .tokenStore(tokenStore);
    }


}

