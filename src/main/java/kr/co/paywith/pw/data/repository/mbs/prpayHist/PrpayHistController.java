package kr.co.paywith.pw.data.repository.mbs.prpayHist;

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
@RequestMapping(value = "/api/prpayHist")
@Api(value = "PrpayHistController", description = "쿠폰 API", basePath = "/api/prpayHist")
public class PrpayHistController extends CommonController {

	 @Autowired
	 PrpayHistRepository prpayHistRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PrpayHistValidator prpayHistValidator;

	 @Autowired
	 PrpayHistService prpayHistService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPrpayHist(
				@RequestBody @Valid PrpayHistDto prpayHistDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  prpayHistValidator.validate(prpayHistDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  PrpayHist prpayHist = modelMapper.map(prpayHistDto, PrpayHist.class);

		  // 레코드 등록
		  PrpayHist newPrpayHist = prpayHistService.create(prpayHist);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PrpayHistController.class).slash(newPrpayHist.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayHistResource prpayHistResource = new PrpayHistResource(newPrpayHist);
		  prpayHistResource.add(linkTo(PrpayHistController.class).withRel("query-prpayHist"));
		  prpayHistResource.add(selfLinkBuilder.withRel("update-prpayHist"));
		  prpayHistResource.add(new Link("/docs/index.html#resources-prpayHist-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(prpayHistResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPrpayHists(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<PrpayHist> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPrpayHist qPrpayHist = QPrpayHist.prpayHist;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPrpayHist.id.eq(searchForm.getId()));
		  }


		  Page<PrpayHist> page = this.prpayHistRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PrpayHistResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-prpayHists-list").withRel("profile"));
		  pagedResources.add(linkTo(PrpayHistController.class).withRel("create-prpayHist"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPrpayHist(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<PrpayHist> prpayHistOptional = this.prpayHistRepository.findById(id);

		  // 고객 정보 체크
		  if (prpayHistOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  PrpayHist prpayHist = prpayHistOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayHistResource prpayHistResource = new PrpayHistResource(prpayHist);
		  prpayHistResource.add(new Link("/docs/index.html#resources-prpayHist-get").withRel("profile"));

		  return ResponseEntity.ok(prpayHistResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPrpayHist(@PathVariable Integer id,
											  @RequestBody @Valid PrpayHistUpdateDto prpayHistUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.prpayHistValidator.validate(prpayHistUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<PrpayHist> prpayHistOptional = this.prpayHistRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (prpayHistOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  PrpayHist existPrpayHist = prpayHistOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  PrpayHist savePrpayHist = this.prpayHistService.update(prpayHistUpdateDto, existPrpayHist);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayHistResource prpayHistResource = new PrpayHistResource(savePrpayHist);
		  prpayHistResource.add(new Link("/docs/index.html#resources-prpayHist-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(prpayHistResource);
	 }
}
