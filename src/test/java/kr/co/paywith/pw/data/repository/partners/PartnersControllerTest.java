package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.common.AppPropertiesTest;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.admin.AdminType;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.reflections.util.ConfigurationBuilder.build;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PartnersControllerTest extends BaseControllerTest {


    @Autowired
    PartnersRepository partnersRepository;

    @Autowired
    PartnersService partnersService;

    @Autowired
    AppPropertiesTest appPropertiesTest;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {

    }



    @Test
    @TestDescription("업체을 등록하는 테스트")
    public void createPartners() throws Exception {

        Company company = new Company();
              company.setId(6);
              company.setCompanyNm("페이위드");
              company.setRepFirstName("Won");
              company.setRepLastName("Che");

        PartnersDto partners =  PartnersDto.builder()

                .adminId("che2@paywith.co.kr")
                .firstName("won")
                .lastName("won")
                .email("che@paywith.co.kr")
                .password("1234")
                .phone("01046940301")
                .company(company)
                .build();

        mockMvc.perform(post("/api/partners/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(partners)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(document("create-admin"))
        ;

    }



    @Test
    @TestDescription("30개의 업체정보를 10개씩 두번째 페이지 조회하기")
    public void getPartnerss() throws Exception {
        // Given
        //	IntStream.range(0, 30).forEach(this::generatePartners);

        // When & Then
        this.mockMvc.perform(get("/api/partners")
                        .header("Origin","*")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,DESC")

        )
                .andDo(print())
                .andExpect(status().isOk())

        ;
    }




    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartners() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/{id}", 1)
                .header("Origin","*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }



    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartnersAdminId() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/adminId/{id}", "won")
                .header("Origin","*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }

    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartnersRequestViews() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/{id}/requests", 1)
                        .header("Origin","*")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "0")
                        .param("size", "100")
                        .param("sort", "id,DESC")
                        .param("step", "6")
                        .param("requestAssignmentsStatusEnum", "견적요청")

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }


    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartnersUpdateName() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);
        // Given
        Partners partners = new Partners();
        partners.setName("WON");

        // When & Then
        this.mockMvc.perform(put("/api/partners/name/{id}", 1)
                .header("Origin","*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(partners))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }

    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartnersRequestList() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/{id}/requestsList", 4)
                        .header("Origin","*")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,DESC")
                        .param("step", "6")
                        .param("requestAssignmentsStatusEnum", "상담요청")
                        .param("requestAssignmentsStatusEnum", "상담완료")

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }



    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getExcelxls() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/excel")
                        .header("Origin","*")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                //	.param("requestAssignmentsStatusEnum", "상담요청")
//				.param("requestAssignmentsStatusEnum", "상담완료")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-an-partners"))
        ;
    }


    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartnersRequestDetail() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/{id}/requests/{requestsId}", 1, 9)
                .header("Origin","*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }


    @Test
    @TestDescription("기존의 업체를 하나 조죄하기")
    public void getPartnersRequestCounts() throws Exception {
        // Given
        //   Partners partners = this.generatePartners(100);

        // When & Then
        this.mockMvc.perform(get("/api/partners/{id}/requestsCount", 2)
                        .header("Origin","*")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "id,DESC")
                        .param("step", "6")
                        .param("requestAssignmentsStatusEnum", "견적요청")
                        .param("requestAssignmentsStatusEnum", "견적완료")
//          	.param("requestAssignmentsStatusEnum", "상담요청")
//				.param("requestAssignmentsStatusEnum", "상담완료")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-partners"))
        ;
    }


    @Test
    @TestDescription("포인트 입력 처리 ")
    public void chargePoint() throws Exception {

        // Given
        PartnersChargeDto partnersDto = new PartnersChargeDto();
        partnersDto.setId(1);
        partnersDto.setChrgAmt(1000000);
        partnersDto.setComment("관리자 화면에서 수동으로 입금 실행 ");

        // When & Then
        this.mockMvc.perform(put("/api/partners/{id}/charge", partnersDto.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(partnersDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-partners"))
        ;
    }


    @Test
    @TestDescription("보너스포인트 입력 처리 ")
    public void chargeBonusPoint() throws Exception {
        // Given
        PartnersChargeDto partnersDto = new PartnersChargeDto();
        partnersDto.setId(2);
        partnersDto.setChrgAmt(10000);
        partnersDto.setComment("관리자 화면에서 수동으로 보너스입금 실행 ");

        // When & Then
        this.mockMvc.perform(put("/api/partners/{id}/chargeBonus", partnersDto.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(partnersDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-partners"))
        ;
    }


    @Test
    @TestDescription("출금(환불) 처리 ")
    public void chargeRefund() throws Exception {
        // Given
        PartnersChargeDto partnersDto = new PartnersChargeDto();
        partnersDto.setId(2);
        partnersDto.setChrgAmt(30000);
        partnersDto.setComment(" 출금 시켜줌 ");

        // When & Then
        this.mockMvc.perform(put("/api/partners/{id}/refund", partnersDto.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(partnersDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-partners"))
        ;
    }

    @Test
    @TestDescription("업체정보를 정상적으로 수정하기")
    public void updatePartners() throws Exception {

        // Given
        PartnersDto partnersDto = new PartnersDto();
        String address = " 변경된 주소 " + LocalDateTime.now();
        partnersDto.setId(2);
        ;

        // When & Then
        this.mockMvc.perform(put("/api/partners/{id}", partnersDto.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(partnersDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("address").value(address))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-partners"))
        ;
    }



    private Admin generateAdmin(int i) {

        Admin admin = Admin.builder()
                .adminId("simji0202" + LocalDateTime.now())
                .adminPw("020202")
                .adminNm("Che Won")
                .adminType(AdminType.PARTNER)
                .roles(Set.of(AdminRole.PARTNER))
                .build();

        return this.adminRepository.save(admin);
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
