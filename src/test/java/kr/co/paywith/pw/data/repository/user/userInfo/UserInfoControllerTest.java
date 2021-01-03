package kr.co.paywith.pw.data.repository.user.userInfo;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.UserAppOsCd;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userApp.UserApp;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserInfoControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.userInfoRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }




    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createUserInfo() throws Exception {

        Grade grade = new Grade();
        grade.setGradeNm("VVIP ");
        grade.setGradeUpValue(2000);


        mockMvc.perform(post("/api/grade/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(grade)))
        ;


        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("che12");
        userInfo.setUserPw("1234");
        userInfo.setUserNm("원이");
        userInfo.setActiveFl(true);
        userInfo.setCertTypeCd(CertTypeCd.CI);


        Grade existGrade = new Grade();
        existGrade.setId(1);

        userInfo.setGrade(existGrade);


        // userInfo.setUserAppList(List.of(userApp, userApp2));

        // 브랜드 설정
        userInfo.setRoles(Set.of(AdminRole.USER));


        mockMvc.perform(post("/api/userInfo/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(userInfo)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getUserInfos() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateUserInfo);

        // When & Then
        this.mockMvc.perform(get("/api/userInfo")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.userInfoList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-userInfos"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getUserInfo() throws Exception {
        // Given
        //  UserInfo userInfo = this.generateUserInfo(100);

        // When & Then
        this.mockMvc.perform(get("/api/userInfo/{id}", 2)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-userInfo"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateUserInfo() throws Exception {

        // Given
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setId(12);
        userInfo.setUserNm(" 변경유정정보 ");

        UserApp userApp2 = new UserApp();
        userApp2.setId(1);

        userInfo.setUserAppList(List.of(userApp2));


        // When & Then
        this.mockMvc.perform(put("/api/userInfo/{id}", userInfo.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(userInfo)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-userInfo"))
        ;
    }


}
