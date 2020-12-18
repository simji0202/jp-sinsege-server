package kr.co.paywith.pw.config;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminService;
import kr.co.paywith.pw.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class AuthServerConfigTest extends BaseControllerTest {


    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트 ")
    public void getAuthToken() throws Exception {

        // Given
        String username = "user" ;
        String password = "1234" ;

        // 인증서버 정보 설정
        String clientId = "sinsege";
        String clientSecret = "paywith1234";

        this.mockMvc.perform(post("/oauth/token")
                .header("Origin","*")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type",  "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())

                ;

    }
}
