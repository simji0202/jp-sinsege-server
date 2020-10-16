package kr.co.paywith.pw.data.repository.requests;

import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminService;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.requests.RequestsDto;
import kr.co.paywith.pw.data.repository.requests.RequestsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Parameter;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RequestsControllerTest extends BaseControllerTest {

    @Autowired
    RequestsRepository requestsRepository;

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

        //       this.requestsRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }



    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createRequests() throws Exception {

        RequestsDto requests = new RequestsDto();
        Partners partners = new Partners();

        partners.setId(1);
        requests.setReqNo("XTE20200604-0004");
        requests.setReqStateCd(RequestStatusEnum.예약확정);
        requests.setDepartDate("20200618");
        requests.setDepartAreaCd("nogoya");
        requests.setTotBus(10);
        requests.setSeaterBus49(3);
        requests.setCruiseShipName("은하철도999");
        requests.setTravalAgencyName("코로나여행사");
        requests.setPartners(partners);

        mockMvc.perform(post("/api/requests/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(requests)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getRequestss() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateRequests);

        // When & Then
        this.mockMvc.perform(get("/api/requests")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,DESC")
                        .param("companyId", "1")
                        .param("fromCreateDate", "2020-08-07 00:00:00")
                        .param("toCreateDate", "2020-08-07 23:59:59")
                        .header("Origin","*")

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())

        ;
    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getRequestssWithAuthentication() throws Exception {

        // Given
        //   IntStream.range(0, 30).forEach(this::generateRequests);

        // When & Then
        this.mockMvc.perform(get("/api/requests")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.requestsList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-requestss"))
        ;
    }



    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getRequestssBusList() throws Exception {

        // Given
        //   IntStream.range(0, 30).forEach(this::generateRequests);

        // When & Then
        this.mockMvc.perform(get("/api/requests/bus/list")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .param("page", "0")
                .param("size", "10000")
                .param("sort", "id,DESC")
                .param("requestsId", "40")
                .param("agentId", "28")


        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andDo(document("query-requestss"))
        ;
    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getRequestssBusWithAuthentication() throws Exception {

        // Given
        //   IntStream.range(0, 30).forEach(this::generateRequests);

        // When & Then
        this.mockMvc.perform(get("/api/requests/bus")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
                .param("agentId", "28")


        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andDo(document("query-requestss"))
        ;
    }




    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getRequests() throws Exception {
        // Given
        //  Requests requests = this.generateRequests(100);

        // When & Then
        this.mockMvc.perform(get("/api/requests/{id}", 40)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-requests"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateRequests() throws Exception {

        Requests requests = this.requestsRepository.findById(15).orElse(null);

        RequestsUpdateDto requestsUpdateDto = this.modelMapper.map(requests, RequestsUpdateDto.class);


        // When & Then
        this.mockMvc.perform(put("/api/requests/{id}", 15)
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .header("Origin","*")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(requestsUpdateDto))

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-requests"))
        ;
    }



    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateRequestStatus() throws Exception {

        // Given
        // When & Then
        this.mockMvc.perform(put("/api/requests/{id}/statusUpdate", 9)
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .header("Origin","*")
                        .param("reqStateCd", "버스수배중")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                //
                //
        )
                .andDo(print())
                .andExpect(status().isOk())
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
        String username = "simji" ;
        String password = "1234" ;

        // 인증서버 정보 설정
        String clientId = "sinsege";
        String clientSecret = "paywith1234";

        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .header("Origin","*")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type",  "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }


    private Admin createAdmin() {
        Admin manager = Admin.builder()
                .adminId(appProperties.getAdminId())
                .adminPw(appProperties.getAdminPw())
                .adminNm("채원")
                .build();
        return this.adminService.saveAdmin(manager);
    }

}
