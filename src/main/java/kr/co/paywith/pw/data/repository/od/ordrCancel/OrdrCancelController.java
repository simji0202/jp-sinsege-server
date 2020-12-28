package kr.co.paywith.pw.data.repository.od.ordrCancel;

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
@RequestMapping(value = "/api/ordrCancel")
@Api(value = "OrdrCancelController", description = "쿠폰 API", basePath = "/api/ordrCancel")
public class OrdrCancelController extends CommonController {

	 @Autowired
	 OrdrCancelRepository ordrCancelRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrCancelValidator ordrCancelValidator;

	 @Autowired
	 OrdrCancelService ordrCancelService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrCancel(
				@RequestBody @Valid OrdrCancelDto ordrCancelDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrCancelValidator.validate(ordrCancelDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrCancel ordrCancel = modelMapper.map(ordrCancelDto, OrdrCancel.class);

		  // 레코드 등록
		  OrdrCancel newOrdrCancel = ordrCancelService.create(ordrCancel);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrCancelController.class).slash(newOrdrCancel.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCancelResource ordrCancelResource = new OrdrCancelResource(newOrdrCancel);
		  ordrCancelResource.add(linkTo(OrdrCancelController.class).withRel("query-ordrCancel"));
		  ordrCancelResource.add(selfLinkBuilder.withRel("update-ordrCancel"));
		  ordrCancelResource.add(new Link("/docs/index.html#resources-ordrCancel-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrCancelResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrCancels(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrCancel> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrCancel qOrdrCancel = QOrdrCancel.ordrCancel;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrCancel.id.eq(searchForm.getId()));
		  }


		  Page<OrdrCancel> page = this.ordrCancelRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrCancelResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrCancels-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrCancelController.class).withRel("create-ordrCancel"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrCancel(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrCancel> ordrCancelOptional = this.ordrCancelRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrCancelOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrCancel ordrCancel = ordrCancelOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCancelResource ordrCancelResource = new OrdrCancelResource(ordrCancel);
		  ordrCancelResource.add(new Link("/docs/index.html#resources-ordrCancel-get").withRel("profile"));

		  return ResponseEntity.ok(ordrCancelResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrCancel(@PathVariable Integer id,
											  @RequestBody @Valid OrdrCancelUpdateDto ordrCancelUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrCancelValidator.validate(ordrCancelUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrCancel> ordrCancelOptional = this.ordrCancelRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrCancelOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrCancel existOrdrCancel = ordrCancelOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrCancel saveOrdrCancel = this.ordrCancelService.update(ordrCancelUpdateDto, existOrdrCancel);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCancelResource ordrCancelResource = new OrdrCancelResource(saveOrdrCancel);
		  ordrCancelResource.add(new Link("/docs/index.html#resources-ordrCancel-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrCancelResource);
	 }
}
