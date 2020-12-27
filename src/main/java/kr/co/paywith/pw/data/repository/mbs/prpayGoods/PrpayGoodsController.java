package kr.co.paywith.pw.data.repository.mbs.prpayGoods;

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
@RequestMapping(value = "/api/prpayGoods")
@Api(value = "PrpayGoodsController", description = "쿠폰 API", basePath = "/api/prpayGoods")
public class PrpayGoodsController extends CommonController {

	 @Autowired
	 PrpayGoodsRepository prpayGoodsRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PrpayGoodsValidator prpayGoodsValidator;

	 @Autowired
	 PrpayGoodsService prpayGoodsService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPrpayGoods(
				@RequestBody @Valid PrpayGoodsDto prpayGoodsDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  prpayGoodsValidator.validate(prpayGoodsDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  PrpayGoods prpayGoods = modelMapper.map(prpayGoodsDto, PrpayGoods.class);

		  // 레코드 등록
		  PrpayGoods newPrpayGoods = prpayGoodsService.create(prpayGoods);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PrpayGoodsController.class).slash(newPrpayGoods.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayGoodsResource prpayGoodsResource = new PrpayGoodsResource(newPrpayGoods);
		  prpayGoodsResource.add(linkTo(PrpayGoodsController.class).withRel("query-prpayGoods"));
		  prpayGoodsResource.add(selfLinkBuilder.withRel("update-prpayGoods"));
		  prpayGoodsResource.add(new Link("/docs/index.html#resources-prpayGoods-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(prpayGoodsResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPrpayGoodss(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<PrpayGoods> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPrpayGoods qPrpayGoods = QPrpayGoods.prpayGoods;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPrpayGoods.id.eq(searchForm.getId()));
		  }


		  Page<PrpayGoods> page = this.prpayGoodsRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PrpayGoodsResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-prpayGoodss-list").withRel("profile"));
		  pagedResources.add(linkTo(PrpayGoodsController.class).withRel("create-prpayGoods"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPrpayGoods(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<PrpayGoods> prpayGoodsOptional = this.prpayGoodsRepository.findById(id);

		  // 고객 정보 체크
		  if (prpayGoodsOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  PrpayGoods prpayGoods = prpayGoodsOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayGoodsResource prpayGoodsResource = new PrpayGoodsResource(prpayGoods);
		  prpayGoodsResource.add(new Link("/docs/index.html#resources-prpayGoods-get").withRel("profile"));

		  return ResponseEntity.ok(prpayGoodsResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPrpayGoods(@PathVariable Integer id,
											  @RequestBody @Valid PrpayGoodsUpdateDto prpayGoodsUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.prpayGoodsValidator.validate(prpayGoodsUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<PrpayGoods> prpayGoodsOptional = this.prpayGoodsRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (prpayGoodsOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  PrpayGoods existPrpayGoods = prpayGoodsOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  PrpayGoods savePrpayGoods = this.prpayGoodsService.update(prpayGoodsUpdateDto, existPrpayGoods);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PrpayGoodsResource prpayGoodsResource = new PrpayGoodsResource(savePrpayGoods);
		  prpayGoodsResource.add(new Link("/docs/index.html#resources-prpayGoods-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(prpayGoodsResource);
	 }
}
