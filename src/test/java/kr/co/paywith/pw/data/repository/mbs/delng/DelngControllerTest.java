package kr.co.paywith.pw.data.repository.mbs.delng;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DelngControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.delngRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }


    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createDelng() throws Exception {


        // 유저가 핸드폰을 통해 상품 하나를  쿠폰을 이용하여 거래
        // 상품코드 : 1   쿠폰 : 금액 쿠폰 500

        DelngDto delng = new DelngDto();

        delng.setConfmNo("20210111000001");
        delng.setDelngTypeCd(DelngTypeCd.APP);

        delng.setDelngAmt(45000);    // 결제 금액  지불해야 할 합계(정산 )  거래 금액

        delng.setCpnId(1);               // 사용할 쿠폰 설정
        delng.setCpnAmt(1000);           // 쿠폰으로 인해서 할인된 금액

        DelngPayment delngPayment = new DelngPayment();
        delngPayment.setDelngPaymentTypeCd(DelngPaymentTypeCd.PG_PAY);
        delngPayment.setAmt(44000);



        mockMvc.perform(post("/api/delng/")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(delng)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        ;

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getDelngs() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateDelng);

        // When & Then
        this.mockMvc.perform(get("/api/delng")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.delngList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-delngs"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getDelng() throws Exception {
        // Given
        //  Delng delng = this.generateDelng(100);

        // When & Then
        this.mockMvc.perform(get("/api/delng/{id}", 1)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-delng"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateDelng() throws Exception {

        // Given
        Delng delng = new Delng();
        delng.setId(1);
        delng.setConfmNo("변경 ");


        // When & Then
        this.mockMvc.perform(put("/api/delng/{id}", delng.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(delng)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-delng"))
        ;
    }


}