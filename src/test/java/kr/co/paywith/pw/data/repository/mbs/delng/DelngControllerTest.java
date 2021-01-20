package kr.co.paywith.pw.data.repository.mbs.delng;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPaymentDto;
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

        DelngGoodsOptMaster delngGoodsOptMaster1 = new DelngGoodsOptMaster();
        delngGoodsOptMaster1.setGoodsOptNm("소 (550g)");
        delngGoodsOptMaster1.setGoodsOptAmt(0);


        DelngGoodsOptMaster delngGoodsOptMaster4 = new DelngGoodsOptMaster();
        delngGoodsOptMaster4.setGoodsOptNm("삼겹");
        delngGoodsOptMaster4.setGoodsOptAmt(0);

        DelngGoodsOptMaster delngGoodsOptMaster5 = new DelngGoodsOptMaster();
        delngGoodsOptMaster5.setGoodsOptNm("목살");
        delngGoodsOptMaster5.setGoodsOptAmt(0);

        DelngGoodsOptMaster delngGoodsOptMaster6 = new DelngGoodsOptMaster();
        delngGoodsOptMaster6.setGoodsOptNm("직갈비");
        delngGoodsOptMaster6.setGoodsOptAmt(0);


        // 사이즈 옵션 선택
        DelngGoodsOpt delngGoodsOpt = new DelngGoodsOpt();
        delngGoodsOpt.setOptTitle("사이즈 선택");
        delngGoodsOpt.setMultiChoiceFl(false);
        delngGoodsOpt.setNeedFl(true);

        delngGoodsOpt.setGoodsOptMasterList(List.of(delngGoodsOptMaster1));


        DelngGoodsOpt delngGoodsOpt2 = new DelngGoodsOpt();
        delngGoodsOpt2.setOptTitle("고기 선택");
        delngGoodsOpt2.setMultiChoiceFl(true);
        delngGoodsOpt2.setNeedFl(false);

        delngGoodsOpt2.setGoodsOptMasterList(List.of(delngGoodsOptMaster4, delngGoodsOptMaster5, delngGoodsOptMaster6));



        DelngGoods delngGoods = new DelngGoods();

        delngGoods.setGoodsId(1);
        delngGoods.setGoodsNm("구매 상품1");
        delngGoods.setGoodsCnt(1);
        delngGoods.setGoodsAmt(10000);
        delngGoods.setDelngGoodsOptList(List.of(delngGoodsOpt, delngGoodsOpt2 ));


        DelngDto delng = new DelngDto();

        delng.setConfmNo("20210111000001");
        delng.setDelngTypeCd(DelngTypeCd.APP);

        delng.setTotalAmt(45000);    // 결제 금액  지불해야 할 합계(정산 )  거래 금액

        delng.setCpnId(1);               // 사용할 쿠폰 설정
        delng.setCpnAmt(1000);           // 쿠폰으로 인해서 할인된 금액

        delng.setDelngGoodsList(List.of(delngGoods));

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);

        delng.setUserInfo(userInfo);


        DelngPaymentDto delngPaymentDto = new DelngPaymentDto();
        delngPaymentDto.setAmt(44000);
        delngPaymentDto.setDelngPaymentTypeCd(DelngPaymentTypeCd.PG_PAY);
        delngPaymentDto.setPayId(3);

        delng.setDelngPaymentList(List.of(delngPaymentDto));



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
