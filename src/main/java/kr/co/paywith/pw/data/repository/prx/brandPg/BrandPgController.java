package kr.co.paywith.pw.data.repository.prx.brandPg;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
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
@RequestMapping(value = "/api/brandPg")
@Api(value = "BrandPgController", description = "쿠폰 API", basePath = "/api/brandPg")
public class BrandPgController extends CommonController {

	 @Autowired
	 BrandPgRepository brandPgRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 BrandPgValidator brandPgValidator;

	 @Autowired
	 BrandPgService brandPgService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createBrandPg(
				@RequestBody @Valid BrandPgDto brandPgDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  brandPgValidator.validate(brandPgDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  BrandPg brandPg = modelMapper.map(brandPgDto, BrandPg.class);

		  // 레코드 등록
		  BrandPg newBrandPg = brandPgService.create(brandPg);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(BrandPgController.class).slash(newBrandPg.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  BrandPgResource brandPgResource = new BrandPgResource(newBrandPg);
		  brandPgResource.add(linkTo(BrandPgController.class).withRel("query-brandPg"));
		  brandPgResource.add(selfLinkBuilder.withRel("update-brandPg"));
		  brandPgResource.add(new Link("/docs/index.html#resources-brandPg-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(brandPgResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getBrandPgs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<BrandPg> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QBrandPg qBrandPg = QBrandPg.brandPg;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qBrandPg.id.eq(searchForm.getId()));
		  }

		  booleanBuilder.and(qBrandPg.brand.id.eq(searchForm.getBrandId()));


		  Page<BrandPg> page = this.brandPgRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new BrandPgResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-brandPgs-list").withRel("profile"));
		  pagedResources.add(linkTo(BrandPgController.class).withRel("create-brandPg"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getBrandPg(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<BrandPg> brandPgOptional = this.brandPgRepository.findById(id);

		  // 고객 정보 체크
		  if (brandPgOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  BrandPg brandPg = brandPgOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  BrandPgResource brandPgResource = new BrandPgResource(brandPg);
		  brandPgResource.add(new Link("/docs/index.html#resources-brandPg-get").withRel("profile"));

		  return ResponseEntity.ok(brandPgResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putBrandPg(@PathVariable Integer id,
											  @RequestBody @Valid BrandPgUpdateDto brandPgUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.brandPgValidator.validate(brandPgUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<BrandPg> brandPgOptional = this.brandPgRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (brandPgOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  BrandPg existBrandPg = brandPgOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  BrandPg saveBrandPg = this.brandPgService.update(brandPgUpdateDto, existBrandPg);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  BrandPgResource brandPgResource = new BrandPgResource(saveBrandPg);
		  brandPgResource.add(new Link("/docs/index.html#resources-brandPg-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(brandPgResource);
	 }
}
