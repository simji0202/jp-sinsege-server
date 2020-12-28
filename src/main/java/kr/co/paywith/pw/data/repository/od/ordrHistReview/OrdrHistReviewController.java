package kr.co.paywith.pw.data.repository.od.ordrHistReview;

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
@RequestMapping(value = "/api/ordrHistReview")
@Api(value = "OrdrHistReviewController", description = "쿠폰 API", basePath = "/api/ordrHistReview")
public class OrdrHistReviewController extends CommonController {

	 @Autowired
	 OrdrHistReviewRepository ordrHistReviewRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrHistReviewValidator ordrHistReviewValidator;

	 @Autowired
	 OrdrHistReviewService ordrHistReviewService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrHistReview(
				@RequestBody @Valid OrdrHistReviewDto ordrHistReviewDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrHistReviewValidator.validate(ordrHistReviewDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrHistReview ordrHistReview = modelMapper.map(ordrHistReviewDto, OrdrHistReview.class);

		  // 레코드 등록
		  OrdrHistReview newOrdrHistReview = ordrHistReviewService.create(ordrHistReview);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrHistReviewController.class).slash(newOrdrHistReview.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistReviewResource ordrHistReviewResource = new OrdrHistReviewResource(newOrdrHistReview);
		  ordrHistReviewResource.add(linkTo(OrdrHistReviewController.class).withRel("query-ordrHistReview"));
		  ordrHistReviewResource.add(selfLinkBuilder.withRel("update-ordrHistReview"));
		  ordrHistReviewResource.add(new Link("/docs/index.html#resources-ordrHistReview-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrHistReviewResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrHistReviews(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrHistReview> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrHistReview qOrdrHistReview = QOrdrHistReview.ordrHistReview;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrHistReview.id.eq(searchForm.getId()));
		  }


		  Page<OrdrHistReview> page = this.ordrHistReviewRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrHistReviewResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrHistReviews-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrHistReviewController.class).withRel("create-ordrHistReview"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrHistReview(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrHistReview> ordrHistReviewOptional = this.ordrHistReviewRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrHistReviewOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrHistReview ordrHistReview = ordrHistReviewOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistReviewResource ordrHistReviewResource = new OrdrHistReviewResource(ordrHistReview);
		  ordrHistReviewResource.add(new Link("/docs/index.html#resources-ordrHistReview-get").withRel("profile"));

		  return ResponseEntity.ok(ordrHistReviewResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrHistReview(@PathVariable Integer id,
											  @RequestBody @Valid OrdrHistReviewUpdateDto ordrHistReviewUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrHistReviewValidator.validate(ordrHistReviewUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrHistReview> ordrHistReviewOptional = this.ordrHistReviewRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrHistReviewOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrHistReview existOrdrHistReview = ordrHistReviewOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrHistReview saveOrdrHistReview = this.ordrHistReviewService.update(ordrHistReviewUpdateDto, existOrdrHistReview);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistReviewResource ordrHistReviewResource = new OrdrHistReviewResource(saveOrdrHistReview);
		  ordrHistReviewResource.add(new Link("/docs/index.html#resources-ordrHistReview-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrHistReviewResource);
	 }
}
