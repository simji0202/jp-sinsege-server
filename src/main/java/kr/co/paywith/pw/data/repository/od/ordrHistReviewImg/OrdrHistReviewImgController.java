package kr.co.paywith.pw.data.repository.od.ordrHistReviewImg;

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
@RequestMapping(value = "/api/ordrHistReviewImg")
@Api(value = "OrdrHistReviewImgController", description = "쿠폰 API", basePath = "/api/ordrHistReviewImg")
public class OrdrHistReviewImgController extends CommonController {

	 @Autowired
	 OrdrHistReviewImgRepository ordrHistReviewImgRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrHistReviewImgValidator ordrHistReviewImgValidator;

	 @Autowired
	 OrdrHistReviewImgService ordrHistReviewImgService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrHistReviewImg(
				@RequestBody @Valid OrdrHistReviewImgDto ordrHistReviewImgDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrHistReviewImgValidator.validate(ordrHistReviewImgDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrHistReviewImg ordrHistReviewImg = modelMapper.map(ordrHistReviewImgDto, OrdrHistReviewImg.class);

		  // 레코드 등록
		  OrdrHistReviewImg newOrdrHistReviewImg = ordrHistReviewImgService.create(ordrHistReviewImg);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrHistReviewImgController.class).slash(newOrdrHistReviewImg.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistReviewImgResource ordrHistReviewImgResource = new OrdrHistReviewImgResource(newOrdrHistReviewImg);
		  ordrHistReviewImgResource.add(linkTo(OrdrHistReviewImgController.class).withRel("query-ordrHistReviewImg"));
		  ordrHistReviewImgResource.add(selfLinkBuilder.withRel("update-ordrHistReviewImg"));
		  ordrHistReviewImgResource.add(new Link("/docs/index.html#resources-ordrHistReviewImg-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrHistReviewImgResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrHistReviewImgs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrHistReviewImg> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrHistReviewImg qOrdrHistReviewImg = QOrdrHistReviewImg.ordrHistReviewImg;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrHistReviewImg.id.eq(searchForm.getId()));
		  }


		  Page<OrdrHistReviewImg> page = this.ordrHistReviewImgRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrHistReviewImgResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrHistReviewImgs-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrHistReviewImgController.class).withRel("create-ordrHistReviewImg"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrHistReviewImg(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrHistReviewImg> ordrHistReviewImgOptional = this.ordrHistReviewImgRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrHistReviewImgOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrHistReviewImg ordrHistReviewImg = ordrHistReviewImgOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistReviewImgResource ordrHistReviewImgResource = new OrdrHistReviewImgResource(ordrHistReviewImg);
		  ordrHistReviewImgResource.add(new Link("/docs/index.html#resources-ordrHistReviewImg-get").withRel("profile"));

		  return ResponseEntity.ok(ordrHistReviewImgResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrHistReviewImg(@PathVariable Integer id,
											  @RequestBody @Valid OrdrHistReviewImgUpdateDto ordrHistReviewImgUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrHistReviewImgValidator.validate(ordrHistReviewImgUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrHistReviewImg> ordrHistReviewImgOptional = this.ordrHistReviewImgRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrHistReviewImgOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrHistReviewImg existOrdrHistReviewImg = ordrHistReviewImgOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrHistReviewImg saveOrdrHistReviewImg = this.ordrHistReviewImgService.update(ordrHistReviewImgUpdateDto, existOrdrHistReviewImg);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistReviewImgResource ordrHistReviewImgResource = new OrdrHistReviewImgResource(saveOrdrHistReviewImg);
		  ordrHistReviewImgResource.add(new Link("/docs/index.html#resources-ordrHistReviewImg-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrHistReviewImgResource);
	 }
}
