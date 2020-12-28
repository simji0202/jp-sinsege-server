package kr.co.paywith.pw.data.repository.od.ordrGoodsOpt;

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
@RequestMapping(value = "/api/ordrGoodsOpt")
@Api(value = "OrdrGoodsOptController", description = "쿠폰 API", basePath = "/api/ordrGoodsOpt")
public class OrdrGoodsOptController extends CommonController {

	 @Autowired
	 OrdrGoodsOptRepository ordrGoodsOptRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrGoodsOptValidator ordrGoodsOptValidator;

	 @Autowired
	 OrdrGoodsOptService ordrGoodsOptService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrGoodsOpt(
				@RequestBody @Valid OrdrGoodsOptDto ordrGoodsOptDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrGoodsOptValidator.validate(ordrGoodsOptDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrGoodsOpt ordrGoodsOpt = modelMapper.map(ordrGoodsOptDto, OrdrGoodsOpt.class);

		  // 레코드 등록
		  OrdrGoodsOpt newOrdrGoodsOpt = ordrGoodsOptService.create(ordrGoodsOpt);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrGoodsOptController.class).slash(newOrdrGoodsOpt.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsOptResource ordrGoodsOptResource = new OrdrGoodsOptResource(newOrdrGoodsOpt);
		  ordrGoodsOptResource.add(linkTo(OrdrGoodsOptController.class).withRel("query-ordrGoodsOpt"));
		  ordrGoodsOptResource.add(selfLinkBuilder.withRel("update-ordrGoodsOpt"));
		  ordrGoodsOptResource.add(new Link("/docs/index.html#resources-ordrGoodsOpt-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrGoodsOptResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrGoodsOpts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrGoodsOpt> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrGoodsOpt qOrdrGoodsOpt = QOrdrGoodsOpt.ordrGoodsOpt;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrGoodsOpt.id.eq(searchForm.getId()));
		  }


		  Page<OrdrGoodsOpt> page = this.ordrGoodsOptRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrGoodsOptResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrGoodsOpts-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrGoodsOptController.class).withRel("create-ordrGoodsOpt"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrGoodsOpt(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrGoodsOpt> ordrGoodsOptOptional = this.ordrGoodsOptRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrGoodsOptOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrGoodsOpt ordrGoodsOpt = ordrGoodsOptOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsOptResource ordrGoodsOptResource = new OrdrGoodsOptResource(ordrGoodsOpt);
		  ordrGoodsOptResource.add(new Link("/docs/index.html#resources-ordrGoodsOpt-get").withRel("profile"));

		  return ResponseEntity.ok(ordrGoodsOptResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrGoodsOpt(@PathVariable Integer id,
											  @RequestBody @Valid OrdrGoodsOptUpdateDto ordrGoodsOptUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrGoodsOptValidator.validate(ordrGoodsOptUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrGoodsOpt> ordrGoodsOptOptional = this.ordrGoodsOptRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrGoodsOptOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrGoodsOpt existOrdrGoodsOpt = ordrGoodsOptOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrGoodsOpt saveOrdrGoodsOpt = this.ordrGoodsOptService.update(ordrGoodsOptUpdateDto, existOrdrGoodsOpt);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsOptResource ordrGoodsOptResource = new OrdrGoodsOptResource(saveOrdrGoodsOpt);
		  ordrGoodsOptResource.add(new Link("/docs/index.html#resources-ordrGoodsOpt-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrGoodsOptResource);
	 }
}
