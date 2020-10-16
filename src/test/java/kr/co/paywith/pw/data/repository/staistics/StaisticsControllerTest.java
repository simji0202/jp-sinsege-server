package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminService;
import kr.co.paywith.pw.data.repository.partners.Partners;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class StaisticsControllerTest  extends BaseControllerTest {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    AppProperties appProperties;


    @Autowired
    private WebApplicationContext webApplicationContext;


    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.staisticsRepository.deleteAll();
        //       this.adminRepository.deleteAll();
    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getStaisticss() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateStaistics);

        // When & Then
        this.mockMvc.perform(get("/api/statistics/front")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))

                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,DESC")
//                .param("name", "노정순")
//                .param("birthday", "1960-04-03")
                        .header("Origin","*")

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.staisticsList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-staisticss"))
        ;
    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getStaisticssWithAuthentication() throws Exception {

        // Given
        //   IntStream.range(0, 30).forEach(this::generateStaistics);

        // When & Then
        this.mockMvc.perform(get("/api/staistics")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.staisticsList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-staisticss"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getStaistics() throws Exception {
        // Given
        //  Staistics staistics = this.generateStaistics(100);

        // When & Then
        this.mockMvc.perform(get("/api/staistics/{id}", 1)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("partnersName").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-staistics"))
        ;
    }




    private String getBearerToken(boolean needToCreateAdmin) throws Exception {
        return "Bearer " + getAccessToken(needToCreateAdmin);
    }

    private String getAccessToken(boolean needToCreateAdmin) throws Exception {
        // Given
        if (needToCreateAdmin) {
            // createAdmin();
        }

        // Given
        String username = "simji";
        String password = "1234";

        // 인증서버 정보 설정
        String clientId = "sinsege";
        String clientSecret = "paywith1234";


        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .header("Origin", "*")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }
}