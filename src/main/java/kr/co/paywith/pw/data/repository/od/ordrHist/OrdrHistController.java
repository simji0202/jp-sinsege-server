package kr.co.paywith.pw.data.repository.od.ordrHist;

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
@RequestMapping(value = "/api/ordrHist")
@Api(value = "OrdrHistController", description = "쿠폰 API", basePath = "/api/ordrHist")
public class OrdrHistController extends CommonController {

	 @Autowired
	 OrdrHistRepository ordrHistRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrHistValidator ordrHistValidator;

	 @Autowired
	 OrdrHistService ordrHistService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrHist(
				@RequestBody @Valid OrdrHistDto ordrHistDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrHistValidator.validate(ordrHistDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrHist ordrHist = modelMapper.map(ordrHistDto, OrdrHist.class);

		  // 레코드 등록
		  OrdrHist newOrdrHist = ordrHistService.create(ordrHist);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrHistController.class).slash(newOrdrHist.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistResource ordrHistResource = new OrdrHistResource(newOrdrHist);
		  ordrHistResource.add(linkTo(OrdrHistController.class).withRel("query-ordrHist"));
		  ordrHistResource.add(selfLinkBuilder.withRel("update-ordrHist"));
		  ordrHistResource.add(new Link("/docs/index.html#resources-ordrHist-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrHistResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrHists(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrHist> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrHist qOrdrHist = QOrdrHist.ordrHist;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrHist.id.eq(searchForm.getId()));
		  }


		  Page<OrdrHist> page = this.ordrHistRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrHistResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrHists-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrHistController.class).withRel("create-ordrHist"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrHist(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrHist> ordrHistOptional = this.ordrHistRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrHistOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrHist ordrHist = ordrHistOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistResource ordrHistResource = new OrdrHistResource(ordrHist);
		  ordrHistResource.add(new Link("/docs/index.html#resources-ordrHist-get").withRel("profile"));

		  return ResponseEntity.ok(ordrHistResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrHist(@PathVariable Integer id,
											  @RequestBody @Valid OrdrHistUpdateDto ordrHistUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrHistValidator.validate(ordrHistUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrHist> ordrHistOptional = this.ordrHistRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrHistOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrHist existOrdrHist = ordrHistOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrHist saveOrdrHist = this.ordrHistService.update(ordrHistUpdateDto, existOrdrHist);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrHistResource ordrHistResource = new OrdrHistResource(saveOrdrHist);
		  ordrHistResource.add(new Link("/docs/index.html#resources-ordrHist-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrHistResource);
	 }
}
