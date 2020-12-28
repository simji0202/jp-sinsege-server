package kr.co.paywith.pw.data.repository.od.ordrPosIf;

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
@RequestMapping(value = "/api/ordrPosIf")
@Api(value = "OrdrPosIfController", description = "쿠폰 API", basePath = "/api/ordrPosIf")
public class OrdrPosIfController extends CommonController {

	 @Autowired
	 OrdrPosIfRepository ordrPosIfRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrPosIfValidator ordrPosIfValidator;

	 @Autowired
	 OrdrPosIfService ordrPosIfService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrPosIf(
				@RequestBody @Valid OrdrPosIfDto ordrPosIfDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrPosIfValidator.validate(ordrPosIfDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrPosIf ordrPosIf = modelMapper.map(ordrPosIfDto, OrdrPosIf.class);

		  // 레코드 등록
		  OrdrPosIf newOrdrPosIf = ordrPosIfService.create(ordrPosIf);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrPosIfController.class).slash(newOrdrPosIf.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrPosIfResource ordrPosIfResource = new OrdrPosIfResource(newOrdrPosIf);
		  ordrPosIfResource.add(linkTo(OrdrPosIfController.class).withRel("query-ordrPosIf"));
		  ordrPosIfResource.add(selfLinkBuilder.withRel("update-ordrPosIf"));
		  ordrPosIfResource.add(new Link("/docs/index.html#resources-ordrPosIf-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrPosIfResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrPosIfs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrPosIf> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrPosIf qOrdrPosIf = QOrdrPosIf.ordrPosIf;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrPosIf.id.eq(searchForm.getId()));
		  }


		  Page<OrdrPosIf> page = this.ordrPosIfRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrPosIfResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrPosIfs-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrPosIfController.class).withRel("create-ordrPosIf"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrPosIf(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrPosIf> ordrPosIfOptional = this.ordrPosIfRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrPosIfOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrPosIf ordrPosIf = ordrPosIfOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrPosIfResource ordrPosIfResource = new OrdrPosIfResource(ordrPosIf);
		  ordrPosIfResource.add(new Link("/docs/index.html#resources-ordrPosIf-get").withRel("profile"));

		  return ResponseEntity.ok(ordrPosIfResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrPosIf(@PathVariable Integer id,
											  @RequestBody @Valid OrdrPosIfUpdateDto ordrPosIfUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrPosIfValidator.validate(ordrPosIfUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrPosIf> ordrPosIfOptional = this.ordrPosIfRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrPosIfOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrPosIf existOrdrPosIf = ordrPosIfOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrPosIf saveOrdrPosIf = this.ordrPosIfService.update(ordrPosIfUpdateDto, existOrdrPosIf);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrPosIfResource ordrPosIfResource = new OrdrPosIfResource(saveOrdrPosIf);
		  ordrPosIfResource.add(new Link("/docs/index.html#resources-ordrPosIf-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrPosIfResource);
	 }
}
