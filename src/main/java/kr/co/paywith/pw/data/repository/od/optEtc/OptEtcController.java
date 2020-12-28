package kr.co.paywith.pw.data.repository.od.optEtc;

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
@RequestMapping(value = "/api/optEtc")
@Api(value = "OptEtcController", description = "쿠폰 API", basePath = "/api/optEtc")
public class OptEtcController extends CommonController {

	 @Autowired
	 OptEtcRepository optEtcRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OptEtcValidator optEtcValidator;

	 @Autowired
	 OptEtcService optEtcService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOptEtc(
				@RequestBody @Valid OptEtcDto optEtcDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  optEtcValidator.validate(optEtcDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OptEtc optEtc = modelMapper.map(optEtcDto, OptEtc.class);

		  // 레코드 등록
		  OptEtc newOptEtc = optEtcService.create(optEtc);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OptEtcController.class).slash(newOptEtc.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OptEtcResource optEtcResource = new OptEtcResource(newOptEtc);
		  optEtcResource.add(linkTo(OptEtcController.class).withRel("query-optEtc"));
		  optEtcResource.add(selfLinkBuilder.withRel("update-optEtc"));
		  optEtcResource.add(new Link("/docs/index.html#resources-optEtc-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(optEtcResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOptEtcs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OptEtc> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOptEtc qOptEtc = QOptEtc.optEtc;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOptEtc.id.eq(searchForm.getId()));
		  }


		  Page<OptEtc> page = this.optEtcRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OptEtcResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-optEtcs-list").withRel("profile"));
		  pagedResources.add(linkTo(OptEtcController.class).withRel("create-optEtc"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOptEtc(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OptEtc> optEtcOptional = this.optEtcRepository.findById(id);

		  // 고객 정보 체크
		  if (optEtcOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OptEtc optEtc = optEtcOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OptEtcResource optEtcResource = new OptEtcResource(optEtc);
		  optEtcResource.add(new Link("/docs/index.html#resources-optEtc-get").withRel("profile"));

		  return ResponseEntity.ok(optEtcResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOptEtc(@PathVariable Integer id,
											  @RequestBody @Valid OptEtcUpdateDto optEtcUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.optEtcValidator.validate(optEtcUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OptEtc> optEtcOptional = this.optEtcRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (optEtcOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OptEtc existOptEtc = optEtcOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OptEtc saveOptEtc = this.optEtcService.update(optEtcUpdateDto, existOptEtc);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OptEtcResource optEtcResource = new OptEtcResource(saveOptEtc);
		  optEtcResource.add(new Link("/docs/index.html#resources-optEtc-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(optEtcResource);
	 }
}
