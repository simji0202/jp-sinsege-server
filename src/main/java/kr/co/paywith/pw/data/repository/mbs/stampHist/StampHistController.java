package kr.co.paywith.pw.data.repository.mbs.stampHist;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandService;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/stampHist")
@Api(value = "StampHistController", description = "쿠폰 API", basePath = "/api/stampHist")
public class StampHistController extends CommonController {

	 @Autowired
	 private StampHistRepository stampHistRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 StampHistValidator stampHistValidator;

	 @Autowired
	 StampHistService stampHistService;

	 @Autowired
	 private BrandService brandService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createStampHist(
				@RequestBody @Valid StampHistDto stampHistDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  stampHistValidator.validate(stampHistDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  StampHist stampHist = modelMapper.map(stampHistDto, StampHist.class);

		  // 레코드 등록
		  StampHist newStampHist = stampHistService.create(stampHist);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(StampHistController.class).slash(newStampHist.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StampHistResource stampHistResource = new StampHistResource(newStampHist);
		  stampHistResource.add(linkTo(StampHistController.class).withRel("query-stampHist"));
		  stampHistResource.add(selfLinkBuilder.withRel("update-stampHist"));
		  stampHistResource.add(new Link("/docs/index.html#resources-stampHist-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(stampHistResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getStampHists(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<StampHist> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QStampHist qStampHist = QStampHist.stampHist;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qStampHist.id.eq(searchForm.getId()));
		  }


		  Page<StampHist> page = this.stampHistRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new StampHistResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-stampHists-list").withRel("profile"));
		  pagedResources.add(linkTo(StampHistController.class).withRel("create-stampHist"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getStampHist(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<StampHist> stampHistOptional = this.stampHistRepository.findById(id);

		  // 고객 정보 체크
		  if (stampHistOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  StampHist stampHist = stampHistOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StampHistResource stampHistResource = new StampHistResource(stampHist);
		  stampHistResource.add(new Link("/docs/index.html#resources-stampHist-get").withRel("profile"));

		  return ResponseEntity.ok(stampHistResource);
	 }


//	 /**
//	  * 정보 변경
//	  */
//	 @PutMapping("/{id}")
//	 public ResponseEntity putStampHist(@PathVariable Integer id,
//											  @RequestBody @Valid StampHistUpdateDto stampHistUpdateDto,
//											  Errors errors,
//											  @CurrentUser Account currentUser) {
//		  // 입력체크
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 논리적 오류 (제약조건) 체크
//		  this.stampHistValidator.validate(stampHistUpdateDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 기존 테이블에서 관련 정보 취득
//		  Optional<StampHist> stampHistOptional = this.stampHistRepository.findById(id);
//
//		  // 기존 정보 유무 체크
//		  if (stampHistOptional.isEmpty()) {
//				// 404 Error return
//				return ResponseEntity.notFound().build();
//		  }
//
//		  // 기존 정보 취득
//		  StampHist existStampHist = stampHistOptional.get();
//
//		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//		  StampHist saveStampHist = this.stampHistService.update(stampHistUpdateDto, existStampHist);
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  StampHistResource stampHistResource = new StampHistResource(saveStampHist);
//		  stampHistResource.add(new Link("/docs/index.html#resources-stampHist-update").withRel("profile"));
//
//		  // 정상적 처리
//		  return ResponseEntity.ok(stampHistResource);
//	 }


	@DeleteMapping("/{id}")
	public ResponseEntity removeStampHist(@PathVariable Integer id,
			Errors errors,
			@CurrentUser Account currentUser) {
		// 입력체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		StampHistDeleteDto stampHistDeleteDto = new StampHistDeleteDto();
		stampHistDeleteDto.setId(id);
		// 논리적 오류 (제약조건) 체크
		this.stampHistValidator.validate(stampHistDeleteDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Optional<StampHist> stampHistOptional = this.stampHistRepository.findById(id);

		if (stampHistOptional.isEmpty()) {
			// 404 Error return
			return ResponseEntity.notFound().build();
		}

		// 취소 가능한 관리자 / 매장 확인
		StampHist stampHist = stampHistOptional.get();
		if (
				(currentUser.getAdmin() != null &&
						!brandService.hasAuthorization(
								currentUser.getAdmin().getBrand(), stampHist.getMrhst().getBrand())) || //
		(currentUser.getMrhstTrmnl() != null &&
				!stampHist.getMrhst().getId()
						.equals(currentUser.getMrhstTrmnl().getMrhst().getId())) || // 거래 매장일 경우
				currentUser.getUserInfo() != null
		){
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		stampHistService.delete(stampHist);

		// 정상적 처리
		return ResponseEntity.ok().build();

	}
}
