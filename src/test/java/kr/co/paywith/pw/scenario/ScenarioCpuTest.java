package kr.co.paywith.pw.scenario;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.enumeration.CpnMasterTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.cm.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.delng.DelngDto;
import kr.co.paywith.pw.data.repository.mbs.delng.DelngGoods;
import kr.co.paywith.pw.data.repository.mbs.delng.DelngGoodsOpt;
import kr.co.paywith.pw.data.repository.mbs.delng.DelngGoodsOptMaster;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPaymentDto;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
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
    cpnMaster.setGoodsId(1);
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
    cpnMaster.setCpnRatio(0);       //    * 쿠폰 할인 비율. 1이상이면 비율로 할인 처리( 0 - 100 까지 )

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


    // 2. 사용 가능한 쿠폰 발급  ( 상품쿠폰, 할인쿠폰, 금액쿠폰등) - 특정상품에만
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

    // 시나리오 데이터 등록 완료

    // 4. 결재 처리
    DelngGoodsOptMaster delngGoodsOptMaster2 = new DelngGoodsOptMaster();
    delngGoodsOptMaster2.setGoodsOptNm("중 (750g)");
    delngGoodsOptMaster2.setGoodsOptAmt(5000);



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

    delngGoodsOpt.setGoodsOptMasterList(List.of(delngGoodsOptMaster2));


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

    delng.setDelngAmt(45000);    // 결제 금액  지불해야 할 합계(정산 )  거래 금액

    delng.setCpnId(1);               // 사용할 쿠폰 설정
    delng.setCpnAmt(1000);           // 쿠폰으로 인해서 할인된 금액

    delng.setDelngGoodsList(List.of(delngGoods));

    userInfo.setId(1);

    delng.setUserInfo(userInfo);

    /// 결제
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

    // end
  }

}
