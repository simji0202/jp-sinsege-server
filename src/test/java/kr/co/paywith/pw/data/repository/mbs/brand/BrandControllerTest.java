package kr.co.paywith.pw.data.repository.mbs.brand;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.enumeration.AvailBrandFnCd;
import kr.co.paywith.pw.data.repository.enumeration.ChrgSetleMthdCd;
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

public class BrandControllerTest extends BaseControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;


    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();

        //       this.brandRepository.deleteAll();
        //       this.adminRepository.deleteAll();

    }


    @Test
    @TestDescription("고객을 등록하는 테스트")
    public void createBrand() throws Exception {


        Brand brand = new Brand();
        brand.setBrandNm("테스트 브랜드 10");
        brand.setActiveFl(true);
        brand.setBrandCd("1234567890123456");
        brand.setAvailBrandFnCdList(List.of(AvailBrandFnCd.CALCU, AvailBrandFnCd.CPN));
        brand.setAvailAppChrgSetleMthdCdList(List.of(ChrgSetleMthdCd.PHONE, ChrgSetleMthdCd.CARD));

        BrandSetting brandSetting = new BrandSetting();
        brandSetting.setBillingMinAmt(3000);
        brandSetting.setBizClass("bizClass");
        brandSetting.setBizType("bizType");
        brandSetting.setFcmKey("FcmKey");


        BrandAuth brandAuth = new BrandAuth();

        brandAuth.setAuthId("simji0202");
        brandAuth.setAuthNm("페이위드 인증업체");


        BrandApp brandApp = new BrandApp();

        brandApp.setAosRefPath("AosRefPaht");
        brandApp.setIosVerNm("IOS");


        brand.setBrandSetting(brandSetting);
        brand.setBrandApp(brandApp);
        brand.setBrandAuth(brandAuth);


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

    }


    @Test
    @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
    public void getBrands() throws Exception {
        // Given
        //  IntStream.range(0, 30).forEach(this::generateBrand);

        // When & Then
        this.mockMvc.perform(get("/api/brand")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.brandList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-brands"))
        ;
    }


    @Test
    @TestDescription("기존의 고객를 하나 조죄하기")
    public void getBrand() throws Exception {
        // Given
        //  Brand brand = this.generateBrand(100);

        // When & Then
        this.mockMvc.perform(get("/api/brand/{id}", 1)
                .header("Origin", "*")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-brand"))
        ;
    }


    @Test
    @TestDescription("고객정보를 정상적으로 수정하기")
    public void updateBrand() throws Exception {

        // Given
        BrandDto brand = new BrandDto();
        brand.setId(2);
        brand.setBrandNm("테스트 브랜드 1");
        brand.setActiveFl(true);
        brand.setBrandCd("1234567890123456");
        brand.setAvailBrandFnCdList(List.of(AvailBrandFnCd.MRHST_IMG));
        brand.setAvailAppChrgSetleMthdCdList(List.of(ChrgSetleMthdCd.PHONE, ChrgSetleMthdCd.CARD));


        // When & Then
        this.mockMvc.perform(put("/api/brand/{id}", brand.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .header("Origin", "*")

                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(brand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-brand"))
        ;
    }

}
