package kr.co.paywith.pw.data.repository.mbs.prpay;

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
@RequestMapping(value = "/api/prpay")
@Api(value = "PrpayController", description = "쿠폰 API", basePath = "/api/prpay")
public class PrpayController extends CommonController {

	 @Autowired
	 PrpayRepository prpayRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PrpayValidator prpayValidator;

	 @Autowired
	 PrpayService prpayService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPrpay(
				@RequestBody @Valid PrpayDto prpayDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  prpayValidator.validate(prpayDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Prpay prpay = modelMapper.map(prpayDto, Prpay.class);

		  // 레코드 등록
		  Prpay newPrpay = prpayService.create(prpay);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PrpayController.class).slash(newPrpay.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayResource prpayResource = new PrpayResource(newPrpay);
		  prpayResource.add(linkTo(PrpayController.class).withRel("query-prpay"));
		  prpayResource.add(selfLinkBuilder.withRel("update-prpay"));
		  prpayResource.add(new Link("/docs/index.html#resources-prpay-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(prpayResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPrpays(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Prpay> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPrpay qPrpay = QPrpay.prpay;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPrpay.id.eq(searchForm.getId()));
		  }


		  Page<Prpay> page = this.prpayRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PrpayResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-prpays-list").withRel("profile"));
		  pagedResources.add(linkTo(PrpayController.class).withRel("create-prpay"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPrpay(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Prpay> prpayOptional = this.prpayRepository.findById(id);

		  // 고객 정보 체크
		  if (prpayOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Prpay prpay = prpayOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayResource prpayResource = new PrpayResource(prpay);
		  prpayResource.add(new Link("/docs/index.html#resources-prpay-get").withRel("profile"));

		  return ResponseEntity.ok(prpayResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPrpay(@PathVariable Integer id,
											  @RequestBody @Valid PrpayUpdateDto prpayUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.prpayValidator.validate(prpayUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Prpay> prpayOptional = this.prpayRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (prpayOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Prpay existPrpay = prpayOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Prpay savePrpay = this.prpayService.update(prpayUpdateDto, existPrpay);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayResource prpayResource = new PrpayResource(savePrpay);
		  prpayResource.add(new Link("/docs/index.html#resources-prpay-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(prpayResource);
	 }
}
