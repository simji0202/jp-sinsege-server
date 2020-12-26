package kr.co.paywith.pw.data.repository.od.goodsOrg;

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
@RequestMapping(value = "/api/goodsOrg")
@Api(value = "GoodsOrgController", description = "쿠폰 API", basePath = "/api/goodsOrg")
public class GoodsOrgController extends CommonController {

	 @Autowired
	 GoodsOrgRepository goodsOrgRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 GoodsOrgValidator goodsOrgValidator;

	 @Autowired
	 GoodsOrgService goodsOrgService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createGoodsOrg(
				@RequestBody @Valid GoodsOrgDto goodsOrgDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  goodsOrgValidator.validate(goodsOrgDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  GoodsOrg goodsOrg = modelMapper.map(goodsOrgDto, GoodsOrg.class);

		  // 레코드 등록
		  GoodsOrg newGoodsOrg = goodsOrgService.create(goodsOrg);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsOrgController.class).slash(newGoodsOrg.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOrgResource goodsOrgResource = new GoodsOrgResource(newGoodsOrg);
		  goodsOrgResource.add(linkTo(GoodsOrgController.class).withRel("query-goodsOrg"));
		  goodsOrgResource.add(selfLinkBuilder.withRel("update-goodsOrg"));
		  goodsOrgResource.add(new Link("/docs/index.html#resources-goodsOrg-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(goodsOrgResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getGoodsOrgs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<GoodsOrg> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QGoodsOrg qGoodsOrg = QGoodsOrg.goodsOrg;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qGoodsOrg.id.eq(searchForm.getId()));
		  }


		  Page<GoodsOrg> page = this.goodsOrgRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new GoodsOrgResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-goodsOrgs-list").withRel("profile"));
		  pagedResources.add(linkTo(GoodsOrgController.class).withRel("create-goodsOrg"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getGoodsOrg(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<GoodsOrg> goodsOrgOptional = this.goodsOrgRepository.findById(id);

		  // 고객 정보 체크
		  if (goodsOrgOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  GoodsOrg goodsOrg = goodsOrgOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOrgResource goodsOrgResource = new GoodsOrgResource(goodsOrg);
		  goodsOrgResource.add(new Link("/docs/index.html#resources-goodsOrg-get").withRel("profile"));

		  return ResponseEntity.ok(goodsOrgResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putGoodsOrg(@PathVariable Integer id,
											  @RequestBody @Valid GoodsOrgUpdateDto goodsOrgUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.goodsOrgValidator.validate(goodsOrgUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<GoodsOrg> goodsOrgOptional = this.goodsOrgRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (goodsOrgOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  GoodsOrg existGoodsOrg = goodsOrgOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  GoodsOrg saveGoodsOrg = this.goodsOrgService.update(goodsOrgUpdateDto, existGoodsOrg);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOrgResource goodsOrgResource = new GoodsOrgResource(saveGoodsOrg);
		  goodsOrgResource.add(new Link("/docs/index.html#resources-goodsOrg-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(goodsOrgResource);
	 }
}
