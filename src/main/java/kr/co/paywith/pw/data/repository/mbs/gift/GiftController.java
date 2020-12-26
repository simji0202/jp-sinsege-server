package kr.co.paywith.pw.data.repository.mbs.gift;

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
@RequestMapping(value = "/api/gift")
@Api(value = "GiftController", description = "쿠폰 API", basePath = "/api/gift")
public class GiftController extends CommonController {

	 @Autowired
	 GiftRepository giftRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 GiftValidator giftValidator;

	 @Autowired
	 GiftService giftService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createGift(
				@RequestBody @Valid GiftDto giftDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  giftValidator.validate(giftDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Gift gift = modelMapper.map(giftDto, Gift.class);

		  // 레코드 등록
		  Gift newGift = giftService.create(gift);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(GiftController.class).slash(newGift.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GiftResource giftResource = new GiftResource(newGift);
		  giftResource.add(linkTo(GiftController.class).withRel("query-gift"));
		  giftResource.add(selfLinkBuilder.withRel("update-gift"));
		  giftResource.add(new Link("/docs/index.html#resources-gift-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(giftResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getGifts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Gift> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QGift qGift = QGift.gift;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qGift.id.eq(searchForm.getId()));
		  }


		  Page<Gift> page = this.giftRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new GiftResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-gifts-list").withRel("profile"));
		  pagedResources.add(linkTo(GiftController.class).withRel("create-gift"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getGift(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Gift> giftOptional = this.giftRepository.findById(id);

		  // 고객 정보 체크
		  if (giftOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Gift gift = giftOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GiftResource giftResource = new GiftResource(gift);
		  giftResource.add(new Link("/docs/index.html#resources-gift-get").withRel("profile"));

		  return ResponseEntity.ok(giftResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putGift(@PathVariable Integer id,
											  @RequestBody @Valid GiftUpdateDto giftUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.giftValidator.validate(giftUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Gift> giftOptional = this.giftRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (giftOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Gift existGift = giftOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Gift saveGift = this.giftService.update(giftUpdateDto, existGift);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GiftResource giftResource = new GiftResource(saveGift);
		  giftResource.add(new Link("/docs/index.html#resources-gift-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(giftResource);
	 }
}
