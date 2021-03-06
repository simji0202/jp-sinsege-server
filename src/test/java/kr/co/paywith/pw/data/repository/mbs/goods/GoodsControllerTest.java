package kr.co.paywith.pw.data.repository.mbs.goods;

import java.util.List;
import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMaster;
import kr.co.paywith.pw.data.repository.mbs.goodsGrp.GoodsGrp;
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

public class GoodsControllerTest extends BaseControllerTest {

	 @Autowired
	 private WebApplicationContext webApplicationContext;

	 // 403 Error 대응
	 @Before
	 public void setUp() throws Exception {
//		  mockMvc = MockMvcBuilders
//					 .webAppContextSetup(webApplicationContext)
//					 .build();

		  //		 this.goodsRepository.deleteAll();
		  //		 this.adminRepository.deleteAll();

	 }


	 @Test
	 @TestDescription("고객을 등록하는 테스트")
	 public void createGoods() throws Exception {

		  Goods goods = new Goods();

		  goods.setGoodsCd("1234567890");
		  goods.setGoodsNm("아메리카노");
		  goods.setGoodsCn("상품옵션을 테스트");
          goods.setGoodsAmt(1000);
          goods.setActiveFl(false);


		 GoodsGrp goodsGrp  = new  GoodsGrp();
		 goodsGrp.setId(1);

		 goods.setGoodsGrp(goodsGrp);

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

     goodsOpt.setGoodsOptMasterList(List.of(goodsOptMaster, goodsOptMaster2));

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

	 }


	 @Test
	 @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
	 public void getGoodss() throws Exception {
		  // Given
		  //  IntStream.range(0, 30).forEach(this::generateGoods);

		  // When & Then
		  this.mockMvc.perform(get("/api/goods")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")
					 .param("page", "0")
					 .param("size", "10")
					 .param("sort", "id,DESC")
		  )
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("page").exists())
					 .andExpect(jsonPath("_embedded.goodsList[0]._links.self").exists())
					 .andExpect(jsonPath("_links.self").exists())
					 .andExpect(jsonPath("_links.profile").exists())
					 .andDo(document("query-goodss"))
		  ;
	 }


	 @Test
	 @TestDescription("기존의 고객를 하나 조죄하기")
	 public void getGoods() throws Exception {
		  // Given
		  //  Goods goods = this.generateGoods(100);

		  // When & Then
		  this.mockMvc.perform(get("/api/goods/{id}", 2)
					 .header("Origin", "*")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
		  )
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("id").exists())
					 .andExpect(jsonPath("_links.self").exists())
					 .andExpect(jsonPath("_links.profile").exists())
					 .andDo(document("get-an-goods"))
		  ;
	 }


	 @Test
	 @TestDescription("고객정보를 정상적으로 수정하기")
	 public void updateGoods() throws Exception {

		  // Given
		  Goods goods = new Goods();
		  goods.setId(1);
		  goods.setGoodsNm(" 변경된 이름 ");



		 GoodsGrp goodsGrp  = new  GoodsGrp();
		 goodsGrp.setId(1);

		 goods.setGoodsGrp(goodsGrp);

		  // When & Then
		  this.mockMvc.perform(put("/api/goods/{id}", 2)
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")

					 .contentType(MediaType.APPLICATION_JSON_UTF8)
					 .content(this.objectMapper.writeValueAsString(goods)))
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("_links.self").exists())
					 .andDo(document("update-goods"))
		  ;
	 }


}
