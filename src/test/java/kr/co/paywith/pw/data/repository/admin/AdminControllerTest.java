package kr.co.paywith.pw.data.repository.admin;


import kr.co.paywith.pw.common.AppPropertiesTest;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;

import kr.co.paywith.pw.data.repository.mbs.enumeration.AuthCd;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


    // 403 Error 대응
    @Before
    public void setUp() throws Exception {

    }

    @Test
    @TestDescription("업체을 등록하는 테스트")
    public void createAdmin() throws Exception {



        Admin admin = Admin.builder()

                .adminId("paywith22")
                .adminPw("1234")
                .adminNm("페이위드 ")
                .authCd(AuthCd.B_MST)
                .roles(Set.of(AdminRole.ADMIN_MASTER))
                .build();


        mockMvc.perform(post("/api/admin/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(admin)))
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
    public void getAdmins() throws Exception {
        // Given
        //	IntStream.range(0, 30).forEach(this::generateAdmin);

        // When & Then
        this.mockMvc.perform(get("/api/admin")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))


                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.adminList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-admins"))
        ;
    }


    @Test
    @TestDescription("기존의 업체를 하나 조회하기")
    public void getAdmin() throws Exception {
        // Given
        //   Admin admin = this.generateAdmin(100);

        // When & Then
        this.mockMvc.perform(get("/api/admin/{id}", 1)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-admin"))
        ;
    }


    @Test
    @TestDescription("업체정보를 정상적으로 수정하기")
    public void updateAdmin() throws Exception {

        // Given

        AdminUpdateDto adminDto = new AdminUpdateDto();
        String address = " 변경된 주소 " + LocalDateTime.now();
        adminDto.setId(2);
        adminDto.setAdminNm("Che Won67");



        // When & Then
        this.mockMvc.perform(put("/api/admin/{id}", 2)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(adminDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-admin"))
        ;
    }


    @Test
    @TestDescription("업체정보를 정상적으로 수정하기")
    public void updateAdminPw() throws Exception {

        // Given

        AdminPwUpdateDto adminDto = new AdminPwUpdateDto();
        adminDto.setAdminPw("1234");

        // When & Then
        this.mockMvc.perform(put("/api/admin/updatePw/{id}", 2)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(adminDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-admin"))
        ;
    }


}
