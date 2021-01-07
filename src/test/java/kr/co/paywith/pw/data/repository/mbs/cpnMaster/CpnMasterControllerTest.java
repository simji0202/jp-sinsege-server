package kr.co.paywith.pw.data.repository.mbs.cpnMaster;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.cpnMasterGoods.CpnMasterGoods;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
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

public class CpnMasterControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.cpnMasterRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }


    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createCpnMaster() throws Exception {

        CpnMaster cpnMaster = new CpnMaster();
        cpnMaster.setCpnNm("커피베이 할인권 ");
        cpnMaster.setCpnCd("1234567890123456");
        cpnMaster.setCpnAmt(50000);
        cpnMaster.setValidDay(20210101);


        // 브랜드 셋팅
        Brand  brand = new Brand();
        brand.setId(1);
        // 브랜든 설정
        cpnMaster.setBrand(brand);


        Goods goods1 = new Goods();
        goods1.setId(1);

        Goods goods2 = new Goods();
        goods2.setId(2);


        CpnMasterGoods cpnMasterGoods = new CpnMasterGoods();
        cpnMasterGoods.setGoods(goods1);

        CpnMasterGoods cpnMasterGoods2 = new CpnMasterGoods();
        cpnMasterGoods2.setGoods(goods2);


        //
        cpnMaster.setCpnMasterGoodsList(List.of(cpnMasterGoods, cpnMasterGoods2));


        mockMvc.perform(post("/api/cpnMaster/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(cpnMaster)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getCpnMasters() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateCpnMaster);

        // When & Then
        this.mockMvc.perform(get("/api/cpnMaster")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.cpnMasterList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-cpnMasters"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getCpnMaster() throws Exception {
        // Given
        //  CpnMaster cpnMaster = this.generateCpnMaster(100);

        // When & Then
        this.mockMvc.perform(get("/api/cpnMaster/{id}", 1)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-cpnMaster"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateCpnMaster() throws Exception {

        // Given
        CpnMasterDto cpnMaster = new CpnMasterDto();
        cpnMaster.setId(1);
        cpnMaster.setCpnNm("커피베이 할인권 ");
        cpnMaster.setCpnCd("1234567890123456");
        cpnMaster.setCpnAmt(50000);
        cpnMaster.setValidDay(20210101);


        Goods goods1 = new Goods();
        goods1.setId(4);



        CpnMasterGoods cpnMasterGoods = new CpnMasterGoods();
        cpnMasterGoods.setGoods(goods1);

        //
        cpnMaster.setCpnMasterGoodsList(List.of(cpnMasterGoods));


        // When & Then
        this.mockMvc.perform(put("/api/cpnMaster/{id}", 1)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(cpnMaster)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-cpnMaster"))
        ;
    }


}