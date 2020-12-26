package kr.co.paywith.pw.data.repository.od.ordrGoodsCpn;

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

public class OrdrGoodsCpnControllerTest extends BaseControllerTest {

	 @Autowired
	 private WebApplicationContext webApplicationContext;

	 // 403 Error 대응
	 @Before
	 public void setUp() throws Exception {
//		  mockMvc = MockMvcBuilders
//					 .webAppContextSetup(webApplicationContext)
//					 .build();

		  //		 this.ordrGoodsCpnRepository.deleteAll();
		  //		 this.adminRepository.deleteAll();

	 }


	 @Test
	 @TestDescription("고객을 등록하는 테스트")
	 public void createOrdrGoodsCpn() throws Exception {

		  OrdrGoodsCpn ordrGoodsCpn = new OrdrGoodsCpn();


		  mockMvc.perform(post("/api/ordrGoodsCpn/")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")
					 .contentType(MediaType.APPLICATION_JSON_UTF8)
					 .accept(MediaTypes.HAL_JSON)
					 .content(objectMapper.writeValueAsString(ordrGoodsCpn)))
					 .andDo(print())
					 .andExpect(status().isCreated())
					 .andExpect(jsonPath("id").exists())
					 .andExpect(header().exists(HttpHeaders.LOCATION))
					 .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
		  ;

	 }


	 @Test
	 @TestDescription("30개의 고객정보를 10개씩 두번째 페이지 조회하기")
	 public void getOrdrGoodsCpns() throws Exception {
		  // Given
		  //  IntStream.range(0, 30).forEach(this::generateOrdrGoodsCpn);

		  // When & Then
		  this.mockMvc.perform(get("/api/ordrGoodsCpn")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")
					 .param("page", "0")
					 .param("size", "10")
					 .param("sort", "id,DESC")
		  )
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("page").exists())
					 .andExpect(jsonPath("_embedded.ordrGoodsCpnList[0]._links.self").exists())
					 .andExpect(jsonPath("_links.self").exists())
					 .andExpect(jsonPath("_links.profile").exists())
					 .andDo(document("query-ordrGoodsCpns"))
		  ;
	 }


	 @Test
	 @TestDescription("기존의 고객를 하나 조죄하기")
	 public void getOrdrGoodsCpn() throws Exception {
		  // Given
		  //  OrdrGoodsCpn ordrGoodsCpn = this.generateOrdrGoodsCpn(100);

		  // When & Then
		  this.mockMvc.perform(get("/api/ordrGoodsCpn/{id}", 1)
					 .header("Origin", "*")
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
		  )
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("id").exists())
					 .andExpect(jsonPath("_links.self").exists())
					 .andExpect(jsonPath("_links.profile").exists())
					 .andDo(document("get-an-ordrGoodsCpn"))
		  ;
	 }


	 @Test
	 @TestDescription("고객정보를 정상적으로 수정하기")
	 public void updateOrdrGoodsCpn() throws Exception {

		  // Given
		  OrdrGoodsCpnDto ordrGoodsCpn = new OrdrGoodsCpnDto();
		  ordrGoodsCpn.setId(1);

		  // When & Then
		  this.mockMvc.perform(put("/api/ordrGoodsCpn/{id}", ordrGoodsCpn.getId())
					 .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
					 .header("Origin", "*")

					 .contentType(MediaType.APPLICATION_JSON_UTF8)
					 .content(this.objectMapper.writeValueAsString(ordrGoodsCpn)))
					 .andDo(print())
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("_links.self").exists())
					 .andDo(document("update-ordrGoodsCpn"))
		  ;
	 }


}
