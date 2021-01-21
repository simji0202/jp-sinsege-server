package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.AdminPwUpdateDto;
import kr.co.paywith.pw.data.repository.user.userApp.UserApp;
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

public class MrhstTrmnlControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.mrhstTrmnlRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }


    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createMrhstTrmnl() throws Exception {

        MrhstTrmnl mrhstTrmnl = new MrhstTrmnl();
        mrhstTrmnl.setUserId("user3");
        mrhstTrmnl.setUserPw("1234");
        mrhstTrmnl.setTrmnlNm("테스트 가맹점 4");
        mrhstTrmnl.setActiveFl(true);

        mockMvc.perform(post("/api/mrhstTrmnl/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(mrhstTrmnl)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getMrhstTrmnls() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateMrhstTrmnl);

        // When & Then
        this.mockMvc.perform(get("/api/mrhstTrmnl")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.mrhstTrmnlList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-mrhstTrmnls"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getMrhstTrmnl() throws Exception {
        // Given
        //  MrhstTrmnl mrhstTrmnl = this.generateMrhstTrmnl(100);

        // When & Then
        this.mockMvc.perform(get("/api/mrhstTrmnl/{id}", 1)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-mrhstTrmnl"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateMrhstTrmnl() throws Exception {

        // Given
        MrhstTrmnlDto mrhstTrmnl = new MrhstTrmnlDto();
        mrhstTrmnl.setTrmnlNm("  정보 ");


        // When & Then
        this.mockMvc.perform(put("/api/mrhstTrmnl/{id}", 1)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(mrhstTrmnl)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-mrhstTrmnl"))
        ;
    }


    @Test
    @TestDescription("업체정보를 정상적으로 수정하기")
    public void updateAdminPw() throws Exception {

        // Given
        MrhstTrmnlPwUpdateDto adminDto = new MrhstTrmnlPwUpdateDto();
        adminDto.setUserPw("1234");

        // When & Then
        this.mockMvc.perform(put("/api/mrhstTrmnl/updatePw/{id}", 1)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(adminDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-mrhstTrmnl"))
        ;
    }


}
