package kr.co.paywith.pw.data.repository.bbs;


import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.admin.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

public class BbsControllerTest extends BaseControllerTest {

  @Autowired
  BbsRepository bbsRepository;

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

    //       this.bbsRepository.deleteAll();
    //       this.adminRepository.deleteAll();

  }



  @Test
  @TestDescription("고객을 등록하는 테스트")
  public void createBbs() throws Exception {

    BbsDto bbs = new BbsDto();

    mockMvc.perform(post("/api/bbs/")
        //           .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin","*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(bbs)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        .andDo(document("create-bbs",
            links(
                linkWithRel("self").description("link to self"),
                linkWithRel("query-bbs").description("link to query bbss"),
                linkWithRel("update-bbs").description("link to update an existing bbs"),
                linkWithRel("profile").description("link to update an existing bbs")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
            ),
            requestFields(
                fieldWithPath("id").description("identifier of new bbs"),
                fieldWithPath("loginId" ).description("로그인ID"),
                fieldWithPath("loginPass" ).description("로그인암호"),
                fieldWithPath("name" ).description("성명"),
                fieldWithPath("gender" ).description("성별"),
                fieldWithPath("birthday" ).description("생일"),
                fieldWithPath("cellPhone" ).description("헨드폰번호"),
                fieldWithPath("telNumber" ).description("자택전화번호"),
                fieldWithPath("email" ).description("이메일주소"),
                fieldWithPath("address" ).description("주소"),
                fieldWithPath("maritalStatus" ).description("결혼여부"),
                fieldWithPath("partnerId" ).description("파트너등록여부"),
                fieldWithPath("familyName" ).description("영문성"),
                fieldWithPath("firstName" ).description("영문이름"),
                fieldWithPath("bbsClass" ).description("고객등급"),
                fieldWithPath("passportNo" ).description("여권번호")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
            ),
            relaxedResponseFields(
                fieldWithPath("id").description("identifier of new bbs"),
                fieldWithPath("loginId" ).description("로그인ID"),
                fieldWithPath("loginPass" ).description("로그인암호"),
                fieldWithPath("name" ).description("성명"),
                fieldWithPath("gender" ).description("성별"),
                fieldWithPath("birthday" ).description("생일"),
                fieldWithPath("cellPhone" ).description("헨드폰번호"),
                fieldWithPath("telNumber" ).description("자택전화번호"),
                fieldWithPath("email" ).description("이메일주소"),
                fieldWithPath("address" ).description("주소"),
                fieldWithPath("maritalStatus" ).description("결혼여부"),
                fieldWithPath("partnerId" ).description("파트너등록여부"),
                fieldWithPath("familyName" ).description("영문성"),
                fieldWithPath("firstName" ).description("영문이름"),
                fieldWithPath("bbsClass" ).description("고객등급"),
                fieldWithPath("passportNo" ).description("여권번호"),
                fieldWithPath("_links.self.href").description("link to self"),
                fieldWithPath("_links.query-bbs.href").description("link to query bbs list"),
                fieldWithPath("_links.update-bbs.href").description("link to update existing bbs"),
                fieldWithPath("_links.profile.href").description("link to profile")
            )
        ))

    ;

  }




  @Test
  @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
  public void getBbss() throws Exception {
    // Given
    //  IntStream.range(0, 30).forEach(this::generateBbs);

    // When & Then
    this.mockMvc.perform(get("/api/bbs")
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
        .andExpect(jsonPath("_embedded.bbsList[0]._links.self").exists())
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.profile").exists())
        .andDo(document("query-bbss"))
    ;
  }


  @Test
  @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
  public void getBbssWithAuthentication() throws Exception {

    // Given
    //   IntStream.range(0, 30).forEach(this::generateBbs);

    // When & Then
    this.mockMvc.perform(get("/api/bbs")
        .header(HttpHeaders.AUTHORIZATION,  "Bearer 3f8a213f-745e-4343-8736-e2eb03815a96")
        .header("Origin","*")
        .param("page", "0")
        .param("size", "10")
        .param("sort", "id,DESC"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("page").exists())
        .andExpect(jsonPath("_embedded.bbsList[0]._links.self").exists())
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.profile").exists())
        .andDo(document("query-bbss"))
    ;
  }


  @Test
  @TestDescription("기존의 고객를 하나 조죄하기")
  public void getBbs() throws Exception {
    // Given
    //  Bbs bbs = this.generateBbs(100);

    // When & Then
    this.mockMvc.perform(get("/api/bbs/{id}", 24702)

        .header("Origin","*")

    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").exists())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.profile").exists())
        .andDo(document("get-an-bbs"))
    ;
  }




  @Test
  @TestDescription("고객정보를 정상적으로 수정하기")
  public void updateBbs() throws Exception {

    // Given

    BbsDto bbsDto = new BbsDto();
    bbsDto.setId(2458);
    String bbsName = "Updated Name";

    // When & Then
    this.mockMvc.perform(put("/api/bbs/{id}", 2458)
            //                 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
            .header("Origin","*")

            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(this.objectMapper.writeValueAsString(bbsDto))
        //
        //
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value(bbsName))
        .andExpect(jsonPath("_links.self").exists())
        .andDo(document("update-bbs"))
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
