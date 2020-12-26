package kr.co.paywith.pw.data.repository.od.userGoodsOpt;

import kr.co.paywith.pw.common.BaseControllerTest;
import kr.co.paywith.pw.common.TestDescription;
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

public class UserGoodsOptControllerTest extends BaseControllerTest {

	 @Autowired
	 private WebApplicationContext webApplicationContext;

	 // 403 Error 대응
	 @Before
	 public void setUp() throws Exception {
//		  mockMvc = MockMvcBuilders
//					 .webAppContextSetup(webApplicationContext)
//					 .build();

		  //		 this.userGoodsOptRepository.deleteAll();
		  //		 this.adminRepository.deleteAll();

	 }


	 @Test
	 @TestDescription("고객을 등록하는 테스트")
	 public void createUserGoodsOpt() throws Exception {

		  UserGoodsOpt userGoodsOpt = new UserGoodsOpt();


		  mockMvc.perform(post("/api/userGoodsOpt/")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")
					 .contentType(MediaType.APPLICATION_JSON_UTF8)
					 .accept(MediaTypes.HAL_JSON)
					 .content(objectMapper.writeValueAsString(userGoodsOpt)))
					 .andDo(print())
					 .andExpect(status().isCreated())
					 .andExpect(jsonPath("id").exists())
					 .andExpect(header().exists(HttpHeaders.LOCATION))
					 .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
		  ;

	 }


	 @Test
	 @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
	 public void getUserGoodsOpts() throws Exception {
		  // Given
		  //  IntStream.range(0, 30).forEach(this::generateUserGoodsOpt);

		  // When & Then
		  this.mockMvc.perform(get("/api/userGoodsOpt")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")
					 .param("page", "0")
					 .param("size", "10")
					 .param("sort", "id,DESC")
		  )
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("page").exists())
					 .andExpect(jsonPath("_embedded.userGoodsOptList[0]._links.self").exists())
					 .andExpect(jsonPath("_links.self").exists())
					 .andExpect(jsonPath("_links.profile").exists())
					 .andDo(document("query-userGoodsOpts"))
		  ;
	 }


	 @Test
	 @TestDescription("기존의 고객를 하나 조죄하기")
	 public void getUserGoodsOpt() throws Exception {
		  // Given
		  //  UserGoodsOpt userGoodsOpt = this.generateUserGoodsOpt(100);

		  // When & Then
		  this.mockMvc.perform(get("/api/userGoodsOpt/{id}", 1)
					 .header("Origin", "*")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
		  )
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("id").exists())
					 .andExpect(jsonPath("_links.self").exists())
					 .andExpect(jsonPath("_links.profile").exists())
					 .andDo(document("get-an-userGoodsOpt"))
		  ;
	 }


	 @Test
	 @TestDescription("고객정보를 정상적으로 수정하기")
	 public void updateUserGoodsOpt() throws Exception {

		  // Given
		  UserGoodsOptDto userGoodsOpt = new UserGoodsOptDto();
		  userGoodsOpt.setId(1);

		  // When & Then
		  this.mockMvc.perform(put("/api/userGoodsOpt/{id}", userGoodsOpt.getId())
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")

					 .contentType(MediaType.APPLICATION_JSON_UTF8)
					 .content(this.objectMapper.writeValueAsString(userGoodsOpt)))
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("_links.self").exists())
					 .andDo(document("update-userGoodsOpt"))
		  ;
	 }


}
