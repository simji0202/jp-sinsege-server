package kr.co.paywith.pw.scenario;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.admin.AdminDto;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.admin.AdminService;
import kr.co.paywith.pw.data.repository.enumeration.AuthCd;
import kr.co.paywith.pw.data.repository.enumeration.AvailBrandFnCd;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.ChrgSetleMthdCd;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandSetting;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.goodsGrp.GoodsGrp;
import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMaster;
import kr.co.paywith.pw.data.repository.user.user.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

public class ScenarioTest extends BaseControllerTest {


  @Autowired
  AdminService adminService;


  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  @TestDescription("업체을 등록하는 테스트")
  public void createScenarioData() throws Exception {


//
//    Admin admin1 = Admin.builder()
//            .adminId("simji")
//            .adminPw("1234")
//            .adminNm("paywith")
//            .roles(Set.of(AdminRole.ADMIN_MASTER))
//            .build();
//
//    this.adminService.create(admin1);



    // 1. 관리자 등록
    AdminDto admin = new AdminDto();

    admin.setAdminId("won2");;
    admin.setAdminPw("1234");
    admin.setAdminNm("페이위드 ");
    admin.setAuthCd(AuthCd.B_MST);
    admin.setRoles(Set.of(AdminRole.ADMIN_MASTER));

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

    // 2. 고객 등록
    UserInfoDto userInfo = new UserInfoDto();
    userInfo.setUserId("che2");
    userInfo.setUserPw("1234");
    userInfo.setUserNm("원이");
    userInfo.setActiveFl(true);
    userInfo.setMobileNum("01046940301");
    userInfo.setCertTypeCd(CertTypeCd.CI);

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


    //3 브랜드 등록
    Brand brand = new Brand();
    brand.setBrandNm("채원의식탁");
    brand.setActiveFl(true);
    brand.setBrandCd("1234567890123456");
    brand.setAvailBrandFnCdList(List.of(AvailBrandFnCd.CALCU, AvailBrandFnCd.CPN));
    brand.setAvailAppChrgSetleMthdCdList(List.of(ChrgSetleMthdCd.PHONE, ChrgSetleMthdCd.CARD));

    BrandSetting brandSetting = new BrandSetting();
    brandSetting.setBizClass("bizClass");
    brandSetting.setBizType("bizType");
    brandSetting.setFcmKey("FcmKey");
    brandSetting.setDanalCpid("simji0202");
    brandSetting.setDanalCppwd("0202");

    brand.setBrandSetting(brandSetting);

    mockMvc.perform(post("/api/brand/")
        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin", "*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(brand)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
    ;

    //4 상품 카테고리1  등록
    GoodsGrp goodsGrp = new GoodsGrp();
    goodsGrp.setGoodsGrpCd("2131325");
    goodsGrp.setGoodsGrpNm("인기메뉴");
    goodsGrp.setActiveFl(false);
    goodsGrp.setSort(1);


    Brand brand2 = new Brand();
    brand2.setId(1);

    goodsGrp.setBrand(brand2);


    mockMvc.perform(post("/api/goodsGrp/")
        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin", "*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(goodsGrp)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
    ;


    // 상품 카테고리2  등록
    GoodsGrp goodsGrp2 = new GoodsGrp();
    goodsGrp2.setGoodsGrpCd("2131325");
    goodsGrp2.setGoodsGrpNm("인기메뉴");
    goodsGrp2.setActiveFl(false);
    goodsGrp2.setSort(1);

    brand2.setId(1);

    goodsGrp2.setBrand(brand2);


    mockMvc.perform(post("/api/goodsGrp/")
        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin", "*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(goodsGrp2)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
    ;


    // 상품 카테고리2  등록
    GoodsGrp goodsGrp3 = new GoodsGrp();
    goodsGrp3.setGoodsGrpCd("2131325");
    goodsGrp3.setGoodsGrpNm("신메");
    goodsGrp3.setActiveFl(false);
    goodsGrp3.setSort(1);

    brand2.setId(1);

    goodsGrp3.setBrand(brand2);


    mockMvc.perform(post("/api/goodsGrp/")
        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin", "*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(goodsGrp3)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
    ;

    //5 상품1   등록
    Goods goods = new Goods();

    goods.setGoodsCd("1234567890");
    goods.setGoodsNm("아메리카노");
    goods.setGoodsCn("상품옵션을 테스트");
    goods.setGoodsAmt(1000);
    goods.setActiveFl(false);
    goods.setStampPlusCnt(1);


    GoodsGrp goodsGrp4  = new  GoodsGrp();
    goodsGrp4.setId(1);

    goods.setGoodsGrp(goodsGrp4);

    GoodsOpt goodsOpt = new GoodsOpt();
    goodsOpt.setOptTitle("기본");
    goodsOpt.setMultiChoiceFl(true);
    goodsOpt.setRequiredFl(false);
    goodsOpt.setActiveFl(true);

    GoodsOptMaster goodsOptMaster = new GoodsOptMaster();
    goodsOptMaster.setGoodsOptNm("Hot");
    goodsOptMaster.setGoodsOptAmt(0);



    GoodsOptMaster goodsOptMaster2 = new GoodsOptMaster();
    goodsOptMaster2.setGoodsOptNm("ice");
    goodsOptMaster2.setGoodsOptAmt(500);

    goodsOpt.setGoodsOptMasters(List.of(goodsOptMaster, goodsOptMaster2));

    goods.setGoodsOptList(List.of(goodsOpt));


    mockMvc.perform(post("/api/goods/")
        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin", "*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(goods)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
    ;

    //6 상품2   등록
    Goods goods2 = new Goods();

    goods2.setGoodsCd("1234567890");
    goods2.setGoodsNm("아메리카노");
    goods2.setGoodsCn("상품옵션을 테스트");
    goods2.setGoodsAmt(1000);
    goods2.setActiveFl(false);
    goods2.setStampPlusCnt(1);

    GoodsGrp goodsGrp41  = new  GoodsGrp();
    goodsGrp41.setId(1);

    goods2.setGoodsGrp(goodsGrp41);

    GoodsOpt goodsOpt2 = new GoodsOpt();
    goodsOpt2.setOptTitle("기본");
    goodsOpt2.setMultiChoiceFl(true);
    goodsOpt2.setRequiredFl(false);
    goodsOpt2.setActiveFl(true);

    GoodsOptMaster goodsOptMaster3 = new GoodsOptMaster();
    goodsOptMaster3.setGoodsOptNm("Hot");
    goodsOptMaster3.setGoodsOptAmt(0);


    GoodsOptMaster goodsOptMaster4 = new GoodsOptMaster();
    goodsOptMaster4.setGoodsOptNm("ice");
    goodsOptMaster4.setGoodsOptAmt(500);

    goodsOpt2.setGoodsOptMasters(List.of(goodsOptMaster3, goodsOptMaster4));

    goods2.setGoodsOptList(List.of(goodsOpt2));


    mockMvc.perform(post("/api/goods/")
        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        .header("Origin", "*")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(goods2)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
    ;

    // 시나리오 데이터 등록 완료
  }

}
