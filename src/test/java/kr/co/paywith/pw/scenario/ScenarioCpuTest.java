package kr.co.paywith.pw.scenario;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.enumeration.CpnMasterTypeCd;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.cm.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

public class ScenarioCpuTest extends BaseControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  @TestDescription("업체을 등록하는 테스트")
  public void createScenarioData() throws Exception {

    // 1. 쿠폰 마스터 등록 ( 상품쿠폰, 할인쿠폰, 금액쿠폰  등  )  - 특정상품에만
    //   1-1  1+1 쿠폰 등록
    CpnMaster cpnMaster = new CpnMaster();
    cpnMaster.setCpnNm("1+1 쿠폰");
    cpnMaster.setCpnMasterTypeCd(CpnMasterTypeCd.GOODS);

    // 쿠폰 코드 (POS연동)
    cpnMaster.setCpnCd("쿠폰코드(POS연동)");
    cpnMaster.setGoodsId("1");
    cpnMaster.setMinUseStdAmt(10000);

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

    //   1-2  할인쿠폰 등록
      cpnMaster.setCpnNm("할인 쿠폰(%)");
      cpnMaster.setCpnMasterTypeCd(CpnMasterTypeCd.RATIO);

      // 쿠폰 코드 (POS연동)
      cpnMaster.setCpnCd("쿠폰코드(POS연동)");
      cpnMaster.setCpnAmt(0);              // 금액 쿠폰 ( 금액 할인 적용)

      cpnMaster.setCpnRatio(10);       //    * 쿠폰 할인 비율. 1이상이면 비율로 할인 처리( 0 - 100 까지 )

      cpnMaster.setValidDay(20220101);   // 유효기간
      cpnMaster.setMinUseStdAmt(10000);

      // 브랜드 셋팅
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


    //   1-3  금액쿠폰 등록
    cpnMaster.setCpnNm("금액 쿠폰");
    cpnMaster.setCpnMasterTypeCd(CpnMasterTypeCd.AMT);


    // 쿠폰 코드 (POS연동)
    cpnMaster.setCpnCd("쿠폰코드(POS연동)");
    cpnMaster.setCpnAmt(500);

    cpnMaster.setValidDay(20220101);   // 유효기간
    cpnMaster.setMinUseStdAmt(10000);

    // 브랜드 셋팅
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


    // 2. 사용 가능한 쿠폰 발급  상품쿠폰, 할인쿠폰, 금액쿠폰  등  )  - 특정상품에만
    // 쿠폰 발행 준비
    // 유저를 설정
    UserInfo userInfo = new UserInfo();
    userInfo.setId(1);

    // 쿠폰 마스터  설정
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
    cpnIssu.setCpnIssuNm(" 쿠폰 발급 테스트 ");
    cpnIssu.setIssuCnt(List.of(cpn, cpn2).size());
    cpnIssu.setCpnList(List.of(cpn, cpn2));


    // 시나리오 데이터 등록 완료






    // end
  }

}