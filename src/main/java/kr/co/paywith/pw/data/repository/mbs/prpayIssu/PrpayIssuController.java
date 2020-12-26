package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

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
@RequestMapping(value = "/api/prpayIssu")
@Api(value = "PrpayIssuController", description = "쿠폰 API", basePath = "/api/prpayIssu")
public class PrpayIssuController extends CommonController {

	 @Autowired
	 PrpayIssuRepository prpayIssuRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PrpayIssuValidator prpayIssuValidator;

	 @Autowired
	 PrpayIssuService prpayIssuService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPrpayIssu(
				@RequestBody @Valid PrpayIssuDto prpayIssuDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  prpayIssuValidator.validate(prpayIssuDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  PrpayIssu prpayIssu = modelMapper.map(prpayIssuDto, PrpayIssu.class);

		  // 레코드 등록
		  PrpayIssu newPrpayIssu = prpayIssuService.create(prpayIssu);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PrpayIssuController.class).slash(newPrpayIssu.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayIssuResource prpayIssuResource = new PrpayIssuResource(newPrpayIssu);
		  prpayIssuResource.add(linkTo(PrpayIssuController.class).withRel("query-prpayIssu"));
		  prpayIssuResource.add(selfLinkBuilder.withRel("update-prpayIssu"));
		  prpayIssuResource.add(new Link("/docs/index.html#resources-prpayIssu-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(prpayIssuResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPrpayIssus(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<PrpayIssu> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPrpayIssu qPrpayIssu = QPrpayIssu.prpayIssu;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPrpayIssu.id.eq(searchForm.getId()));
		  }


		  Page<PrpayIssu> page = this.prpayIssuRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PrpayIssuResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-prpayIssus-list").withRel("profile"));
		  pagedResources.add(linkTo(PrpayIssuController.class).withRel("create-prpayIssu"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPrpayIssu(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<PrpayIssu> prpayIssuOptional = this.prpayIssuRepository.findById(id);

		  // 고객 정보 체크
		  if (prpayIssuOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  PrpayIssu prpayIssu = prpayIssuOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayIssuResource prpayIssuResource = new PrpayIssuResource(prpayIssu);
		  prpayIssuResource.add(new Link("/docs/index.html#resources-prpayIssu-get").withRel("profile"));

		  return ResponseEntity.ok(prpayIssuResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPrpayIssu(@PathVariable Integer id,
											  @RequestBody @Valid PrpayIssuUpdateDto prpayIssuUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.prpayIssuValidator.validate(prpayIssuUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<PrpayIssu> prpayIssuOptional = this.prpayIssuRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (prpayIssuOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  PrpayIssu existPrpayIssu = prpayIssuOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  PrpayIssu savePrpayIssu = this.prpayIssuService.update(prpayIssuUpdateDto, existPrpayIssu);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayIssuResource prpayIssuResource = new PrpayIssuResource(savePrpayIssu);
		  prpayIssuResource.add(new Link("/docs/index.html#resources-prpayIssu-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(prpayIssuResource);
	 }
}
