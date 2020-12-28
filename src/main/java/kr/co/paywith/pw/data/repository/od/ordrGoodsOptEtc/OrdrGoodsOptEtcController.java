package kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc;

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
@RequestMapping(value = "/api/ordrGoodsOptEtc")
@Api(value = "OrdrGoodsOptEtcController", description = "쿠폰 API", basePath = "/api/ordrGoodsOptEtc")
public class OrdrGoodsOptEtcController extends CommonController {

	 @Autowired
	 OrdrGoodsOptEtcRepository ordrGoodsOptEtcRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrGoodsOptEtcValidator ordrGoodsOptEtcValidator;

	 @Autowired
	 OrdrGoodsOptEtcService ordrGoodsOptEtcService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrGoodsOptEtc(
				@RequestBody @Valid OrdrGoodsOptEtcDto ordrGoodsOptEtcDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrGoodsOptEtcValidator.validate(ordrGoodsOptEtcDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrGoodsOptEtc ordrGoodsOptEtc = modelMapper.map(ordrGoodsOptEtcDto, OrdrGoodsOptEtc.class);

		  // 레코드 등록
		  OrdrGoodsOptEtc newOrdrGoodsOptEtc = ordrGoodsOptEtcService.create(ordrGoodsOptEtc);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrGoodsOptEtcController.class).slash(newOrdrGoodsOptEtc.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsOptEtcResource ordrGoodsOptEtcResource = new OrdrGoodsOptEtcResource(newOrdrGoodsOptEtc);
		  ordrGoodsOptEtcResource.add(linkTo(OrdrGoodsOptEtcController.class).withRel("query-ordrGoodsOptEtc"));
		  ordrGoodsOptEtcResource.add(selfLinkBuilder.withRel("update-ordrGoodsOptEtc"));
		  ordrGoodsOptEtcResource.add(new Link("/docs/index.html#resources-ordrGoodsOptEtc-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrGoodsOptEtcResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrGoodsOptEtcs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrGoodsOptEtc> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrGoodsOptEtc qOrdrGoodsOptEtc = QOrdrGoodsOptEtc.ordrGoodsOptEtc;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrGoodsOptEtc.id.eq(searchForm.getId()));
		  }


		  Page<OrdrGoodsOptEtc> page = this.ordrGoodsOptEtcRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrGoodsOptEtcResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrGoodsOptEtcs-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrGoodsOptEtcController.class).withRel("create-ordrGoodsOptEtc"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrGoodsOptEtc(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrGoodsOptEtc> ordrGoodsOptEtcOptional = this.ordrGoodsOptEtcRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrGoodsOptEtcOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrGoodsOptEtc ordrGoodsOptEtc = ordrGoodsOptEtcOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsOptEtcResource ordrGoodsOptEtcResource = new OrdrGoodsOptEtcResource(ordrGoodsOptEtc);
		  ordrGoodsOptEtcResource.add(new Link("/docs/index.html#resources-ordrGoodsOptEtc-get").withRel("profile"));

		  return ResponseEntity.ok(ordrGoodsOptEtcResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrGoodsOptEtc(@PathVariable Integer id,
											  @RequestBody @Valid OrdrGoodsOptEtcUpdateDto ordrGoodsOptEtcUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrGoodsOptEtcValidator.validate(ordrGoodsOptEtcUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrGoodsOptEtc> ordrGoodsOptEtcOptional = this.ordrGoodsOptEtcRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrGoodsOptEtcOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrGoodsOptEtc existOrdrGoodsOptEtc = ordrGoodsOptEtcOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrGoodsOptEtc saveOrdrGoodsOptEtc = this.ordrGoodsOptEtcService.update(ordrGoodsOptEtcUpdateDto, existOrdrGoodsOptEtc);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsOptEtcResource ordrGoodsOptEtcResource = new OrdrGoodsOptEtcResource(saveOrdrGoodsOptEtc);
		  ordrGoodsOptEtcResource.add(new Link("/docs/index.html#resources-ordrGoodsOptEtc-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrGoodsOptEtcResource);
	 }
}
