package kr.co.paywith.pw.data.repository.mbs.cpnRule;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.mbs.cpnRule.CpnRule;
import kr.co.paywith.pw.data.repository.mbs.cpnRule.CpnRuleDto;
import kr.co.paywith.pw.data.repository.mbs.enumeration.CpnIssuRuleCd;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CpnRuleControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.cpnRuleRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }


    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createCpnRule() throws Exception {

        CpnRule cpnRule = new CpnRule();
        cpnRule.setRuleNm("RuleNm");
        cpnRule.setCpnIssuRuleCd(CpnIssuRuleCd.C);


        mockMvc.perform(post("/api/cpnRule/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(cpnRule)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getCpnRules() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateCpnRule);

        // When & Then
        this.mockMvc.perform(get("/api/cpnRule")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.cpnRuleList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-cpnRules"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getCpnRule() throws Exception {
        // Given
        //  CpnRule cpnRule = this.generateCpnRule(100);

        // When & Then
        this.mockMvc.perform(get("/api/cpnRule/{id}", 1)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-cpnRule"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateCpnRule() throws Exception {

        // Given
        CpnRuleDto cpnRule = new CpnRuleDto();
        cpnRule.setId(1);
        cpnRule.setRuleNm("정보변경");

        // When & Then
        this.mockMvc.perform(put("/api/cpnRule/{id}", cpnRule.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(cpnRule)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-cpnRule"))
        ;
    }


}