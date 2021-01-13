package kr.co.paywith.pw.scenario;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

public class ScenarioCpuTest extends BaseControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  @TestDescription("업체을 등록하는 테스트")
  public void createScenarioData() throws Exception {

    // 1. 쿠폰 마스터 등록 ( 상품쿠폰, 할인쿠폰, 금액쿠폰  등  )  - 특정상품에만

    CpnMaster cpnMaster = new CpnMaster();
    cpnMaster.setCpnNm("1+1 쿠폰");

    // 쿠폰 코드 (POS연동)
    cpnMaster.setCpnCd("쿠폰코드(POS연동)");
    cpnMaster.setCpnAmt(50000);
    cpnMaster.setValidDay(20220101);

    // 브랜드 셋팅
    Brand brand = new Brand();
    brand.setId(1);
    // 브랜든 설정
    cpnMaster.setBrand(brand);



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

    // 시나리오 데이터 등록 완료
  }

}
