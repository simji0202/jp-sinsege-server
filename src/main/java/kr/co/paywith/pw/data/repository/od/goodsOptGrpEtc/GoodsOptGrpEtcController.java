package kr.co.paywith.pw.data.repository.od.goodsOptGrpEtc;

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
@RequestMapping(value = "/api/goodsOptGrpEtc")
@Api(value = "GoodsOptGrpEtcController", description = "쿠폰 API", basePath = "/api/goodsOptGrpEtc")
public class GoodsOptGrpEtcController extends CommonController {

	 @Autowired
	 GoodsOptGrpEtcRepository goodsOptGrpEtcRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 GoodsOptGrpEtcValidator goodsOptGrpEtcValidator;

	 @Autowired
	 GoodsOptGrpEtcService goodsOptGrpEtcService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createGoodsOptGrpEtc(
				@RequestBody @Valid GoodsOptGrpEtcDto goodsOptGrpEtcDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  goodsOptGrpEtcValidator.validate(goodsOptGrpEtcDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  GoodsOptGrpEtc goodsOptGrpEtc = modelMapper.map(goodsOptGrpEtcDto, GoodsOptGrpEtc.class);

		  // 레코드 등록
		  GoodsOptGrpEtc newGoodsOptGrpEtc = goodsOptGrpEtcService.create(goodsOptGrpEtc);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsOptGrpEtcController.class).slash(newGoodsOptGrpEtc.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptGrpEtcResource goodsOptGrpEtcResource = new GoodsOptGrpEtcResource(newGoodsOptGrpEtc);
		  goodsOptGrpEtcResource.add(linkTo(GoodsOptGrpEtcController.class).withRel("query-goodsOptGrpEtc"));
		  goodsOptGrpEtcResource.add(selfLinkBuilder.withRel("update-goodsOptGrpEtc"));
		  goodsOptGrpEtcResource.add(new Link("/docs/index.html#resources-goodsOptGrpEtc-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(goodsOptGrpEtcResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getGoodsOptGrpEtcs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<GoodsOptGrpEtc> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QGoodsOptGrpEtc qGoodsOptGrpEtc = QGoodsOptGrpEtc.goodsOptGrpEtc;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qGoodsOptGrpEtc.id.eq(searchForm.getId()));
		  }


		  Page<GoodsOptGrpEtc> page = this.goodsOptGrpEtcRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new GoodsOptGrpEtcResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-goodsOptGrpEtcs-list").withRel("profile"));
		  pagedResources.add(linkTo(GoodsOptGrpEtcController.class).withRel("create-goodsOptGrpEtc"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getGoodsOptGrpEtc(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<GoodsOptGrpEtc> goodsOptGrpEtcOptional = this.goodsOptGrpEtcRepository.findById(id);

		  // 고객 정보 체크
		  if (goodsOptGrpEtcOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  GoodsOptGrpEtc goodsOptGrpEtc = goodsOptGrpEtcOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptGrpEtcResource goodsOptGrpEtcResource = new GoodsOptGrpEtcResource(goodsOptGrpEtc);
		  goodsOptGrpEtcResource.add(new Link("/docs/index.html#resources-goodsOptGrpEtc-get").withRel("profile"));

		  return ResponseEntity.ok(goodsOptGrpEtcResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putGoodsOptGrpEtc(@PathVariable Integer id,
											  @RequestBody @Valid GoodsOptGrpEtcUpdateDto goodsOptGrpEtcUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.goodsOptGrpEtcValidator.validate(goodsOptGrpEtcUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<GoodsOptGrpEtc> goodsOptGrpEtcOptional = this.goodsOptGrpEtcRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (goodsOptGrpEtcOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  GoodsOptGrpEtc existGoodsOptGrpEtc = goodsOptGrpEtcOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  GoodsOptGrpEtc saveGoodsOptGrpEtc = this.goodsOptGrpEtcService.update(goodsOptGrpEtcUpdateDto, existGoodsOptGrpEtc);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptGrpEtcResource goodsOptGrpEtcResource = new GoodsOptGrpEtcResource(saveGoodsOptGrpEtc);
		  goodsOptGrpEtcResource.add(new Link("/docs/index.html#resources-goodsOptGrpEtc-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(goodsOptGrpEtcResource);
	 }
}
