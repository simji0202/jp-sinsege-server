package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cm.CpnMaster;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CpnIssuControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.cpnIssuRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }


    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createCpnIssu() throws Exception {


        // 쿠폰 발행 준비
        // 유저를 설정
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);

        // 쿠폰 마스터  설정
        CpnMaster cpnMaster = new CpnMaster();
        cpnMaster.setId(1);

        // 발행 쿠폰 설정 1
        Cpn cpn = new Cpn();
        cpn.setUserInfo(userInfo);
        cpn.setCpnMaster(cpnMaster);


        // 발행 쿠폰 설정 2
        Cpn cpn2 = new Cpn();
        cpn2.setUserInfo(userInfo);
        cpn2.setCpnMaster(cpnMaster);

        CpnIssu cpnIssu = new CpnIssu();
        cpnIssu.setCpnIssuNm(" 쿠폰 발급 테스트1 ");
        cpnIssu.setIssuCnt(2);
        cpnIssu.setCpnList(List.of(cpn, cpn2));



        mockMvc.perform(post("/api/cpnIssu/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(cpnIssu)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getCpnIssus() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateCpnIssu);

        // When & Then
        this.mockMvc.perform(get("/api/cpnIssu")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.cpnIssuList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-cpnIssus"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getCpnIssu() throws Exception {
        // Given
        //  CpnIssu cpnIssu = this.generateCpnIssu(100);

        // When & Then
        this.mockMvc.perform(get("/api/cpnIssu/{id}", 3)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-cpnIssu"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateCpnIssu() throws Exception {

        // Given
        CpnIssu cpnIssu = new CpnIssu();
        cpnIssu.setId(8);
        cpnIssu.setCpnIssuNm("정보변경");

        Cpn cpn = new Cpn();
        cpn.setId(5);

        cpnIssu.setCpnList(List.of(cpn));



        // When & Then
        this.mockMvc.perform(put("/api/cpnIssu/{id}", 3)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(cpnIssu)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-cpnIssu"))
        ;
    }


}