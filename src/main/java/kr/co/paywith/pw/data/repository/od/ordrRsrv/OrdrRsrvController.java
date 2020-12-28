package kr.co.paywith.pw.data.repository.od.ordrRsrv;

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
@RequestMapping(value = "/api/ordrRsrv")
@Api(value = "OrdrRsrvController", description = "쿠폰 API", basePath = "/api/ordrRsrv")
public class OrdrRsrvController extends CommonController {

	 @Autowired
	 OrdrRsrvRepository ordrRsrvRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrRsrvValidator ordrRsrvValidator;

	 @Autowired
	 OrdrRsrvService ordrRsrvService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrRsrv(
				@RequestBody @Valid OrdrRsrvDto ordrRsrvDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrRsrvValidator.validate(ordrRsrvDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrRsrv ordrRsrv = modelMapper.map(ordrRsrvDto, OrdrRsrv.class);

		  // 레코드 등록
		  OrdrRsrv newOrdrRsrv = ordrRsrvService.create(ordrRsrv);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrRsrvController.class).slash(newOrdrRsrv.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrRsrvResource ordrRsrvResource = new OrdrRsrvResource(newOrdrRsrv);
		  ordrRsrvResource.add(linkTo(OrdrRsrvController.class).withRel("query-ordrRsrv"));
		  ordrRsrvResource.add(selfLinkBuilder.withRel("update-ordrRsrv"));
		  ordrRsrvResource.add(new Link("/docs/index.html#resources-ordrRsrv-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrRsrvResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrRsrvs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrRsrv> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrRsrv qOrdrRsrv = QOrdrRsrv.ordrRsrv;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrRsrv.id.eq(searchForm.getId()));
		  }


		  Page<OrdrRsrv> page = this.ordrRsrvRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrRsrvResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrRsrvs-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrRsrvController.class).withRel("create-ordrRsrv"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrRsrv(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrRsrv> ordrRsrvOptional = this.ordrRsrvRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrRsrvOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrRsrv ordrRsrv = ordrRsrvOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrRsrvResource ordrRsrvResource = new OrdrRsrvResource(ordrRsrv);
		  ordrRsrvResource.add(new Link("/docs/index.html#resources-ordrRsrv-get").withRel("profile"));

		  return ResponseEntity.ok(ordrRsrvResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrRsrv(@PathVariable Integer id,
											  @RequestBody @Valid OrdrRsrvUpdateDto ordrRsrvUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrRsrvValidator.validate(ordrRsrvUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrRsrv> ordrRsrvOptional = this.ordrRsrvRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrRsrvOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrRsrv existOrdrRsrv = ordrRsrvOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrRsrv saveOrdrRsrv = this.ordrRsrvService.update(ordrRsrvUpdateDto, existOrdrRsrv);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrRsrvResource ordrRsrvResource = new OrdrRsrvResource(saveOrdrRsrv);
		  ordrRsrvResource.add(new Link("/docs/index.html#resources-ordrRsrv-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrRsrvResource);
	 }
}
