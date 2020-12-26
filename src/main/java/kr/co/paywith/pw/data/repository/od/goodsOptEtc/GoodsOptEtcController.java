package kr.co.paywith.pw.data.repository.od.goodsOptEtc;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/goodsOptEtc")
@Api(value = "GoodsOptEtcController", description = "쿠폰 API", basePath = "/api/goodsOptEtc")
public class GoodsOptEtcController extends CommonController {

	 @Autowired
	 GoodsOptEtcRepository goodsOptEtcRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 GoodsOptEtcValidator goodsOptEtcValidator;

	 @Autowired
	 GoodsOptEtcService goodsOptEtcService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createGoodsOptEtc(
				@RequestBody @Valid GoodsOptEtcDto goodsOptEtcDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  goodsOptEtcValidator.validate(goodsOptEtcDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  GoodsOptEtc goodsOptEtc = modelMapper.map(goodsOptEtcDto, GoodsOptEtc.class);

		  // 레코드 등록
		  GoodsOptEtc newGoodsOptEtc = goodsOptEtcService.create(goodsOptEtc);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsOptEtcController.class).slash(newGoodsOptEtc.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptEtcResource goodsOptEtcResource = new GoodsOptEtcResource(newGoodsOptEtc);
		  goodsOptEtcResource.add(linkTo(GoodsOptEtcController.class).withRel("query-goodsOptEtc"));
		  goodsOptEtcResource.add(selfLinkBuilder.withRel("update-goodsOptEtc"));
		  goodsOptEtcResource.add(new Link("/docs/index.html#resources-goodsOptEtc-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(goodsOptEtcResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getGoodsOptEtcs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<GoodsOptEtc> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QGoodsOptEtc qGoodsOptEtc = QGoodsOptEtc.goodsOptEtc;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qGoodsOptEtc.id.eq(searchForm.getId()));
		  }


		  Page<GoodsOptEtc> page = this.goodsOptEtcRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new GoodsOptEtcResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-goodsOptEtcs-list").withRel("profile"));
		  pagedResources.add(linkTo(GoodsOptEtcController.class).withRel("create-goodsOptEtc"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getGoodsOptEtc(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<GoodsOptEtc> goodsOptEtcOptional = this.goodsOptEtcRepository.findById(id);

		  // 고객 정보 체크
		  if (goodsOptEtcOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  GoodsOptEtc goodsOptEtc = goodsOptEtcOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptEtcResource goodsOptEtcResource = new GoodsOptEtcResource(goodsOptEtc);
		  goodsOptEtcResource.add(new Link("/docs/index.html#resources-goodsOptEtc-get").withRel("profile"));

		  return ResponseEntity.ok(goodsOptEtcResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putGoodsOptEtc(@PathVariable Integer id,
											  @RequestBody @Valid GoodsOptEtcUpdateDto goodsOptEtcUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.goodsOptEtcValidator.validate(goodsOptEtcUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<GoodsOptEtc> goodsOptEtcOptional = this.goodsOptEtcRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (goodsOptEtcOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  GoodsOptEtc existGoodsOptEtc = goodsOptEtcOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  GoodsOptEtc saveGoodsOptEtc = this.goodsOptEtcService.update(goodsOptEtcUpdateDto, existGoodsOptEtc);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptEtcResource goodsOptEtcResource = new GoodsOptEtcResource(saveGoodsOptEtc);
		  goodsOptEtcResource.add(new Link("/docs/index.html#resources-goodsOptEtc-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(goodsOptEtcResource);
	 }
}
