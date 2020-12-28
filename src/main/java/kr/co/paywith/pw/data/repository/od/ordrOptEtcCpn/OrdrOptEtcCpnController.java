package kr.co.paywith.pw.data.repository.od.ordrOptEtcCpn;

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
@RequestMapping(value = "/api/ordrOptEtcCpn")
@Api(value = "OrdrOptEtcCpnController", description = "쿠폰 API", basePath = "/api/ordrOptEtcCpn")
public class OrdrOptEtcCpnController extends CommonController {

	 @Autowired
	 OrdrOptEtcCpnRepository ordrOptEtcCpnRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrOptEtcCpnValidator ordrOptEtcCpnValidator;

	 @Autowired
	 OrdrOptEtcCpnService ordrOptEtcCpnService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrOptEtcCpn(
				@RequestBody @Valid OrdrOptEtcCpnDto ordrOptEtcCpnDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrOptEtcCpnValidator.validate(ordrOptEtcCpnDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrOptEtcCpn ordrOptEtcCpn = modelMapper.map(ordrOptEtcCpnDto, OrdrOptEtcCpn.class);

		  // 레코드 등록
		  OrdrOptEtcCpn newOrdrOptEtcCpn = ordrOptEtcCpnService.create(ordrOptEtcCpn);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrOptEtcCpnController.class).slash(newOrdrOptEtcCpn.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrOptEtcCpnResource ordrOptEtcCpnResource = new OrdrOptEtcCpnResource(newOrdrOptEtcCpn);
		  ordrOptEtcCpnResource.add(linkTo(OrdrOptEtcCpnController.class).withRel("query-ordrOptEtcCpn"));
		  ordrOptEtcCpnResource.add(selfLinkBuilder.withRel("update-ordrOptEtcCpn"));
		  ordrOptEtcCpnResource.add(new Link("/docs/index.html#resources-ordrOptEtcCpn-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrOptEtcCpnResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrOptEtcCpns(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrOptEtcCpn> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrOptEtcCpn qOrdrOptEtcCpn = QOrdrOptEtcCpn.ordrOptEtcCpn;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrOptEtcCpn.id.eq(searchForm.getId()));
		  }


		  Page<OrdrOptEtcCpn> page = this.ordrOptEtcCpnRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrOptEtcCpnResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrOptEtcCpns-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrOptEtcCpnController.class).withRel("create-ordrOptEtcCpn"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrOptEtcCpn(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrOptEtcCpn> ordrOptEtcCpnOptional = this.ordrOptEtcCpnRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrOptEtcCpnOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrOptEtcCpn ordrOptEtcCpn = ordrOptEtcCpnOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrOptEtcCpnResource ordrOptEtcCpnResource = new OrdrOptEtcCpnResource(ordrOptEtcCpn);
		  ordrOptEtcCpnResource.add(new Link("/docs/index.html#resources-ordrOptEtcCpn-get").withRel("profile"));

		  return ResponseEntity.ok(ordrOptEtcCpnResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrOptEtcCpn(@PathVariable Integer id,
											  @RequestBody @Valid OrdrOptEtcCpnUpdateDto ordrOptEtcCpnUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrOptEtcCpnValidator.validate(ordrOptEtcCpnUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrOptEtcCpn> ordrOptEtcCpnOptional = this.ordrOptEtcCpnRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrOptEtcCpnOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrOptEtcCpn existOrdrOptEtcCpn = ordrOptEtcCpnOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrOptEtcCpn saveOrdrOptEtcCpn = this.ordrOptEtcCpnService.update(ordrOptEtcCpnUpdateDto, existOrdrOptEtcCpn);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrOptEtcCpnResource ordrOptEtcCpnResource = new OrdrOptEtcCpnResource(saveOrdrOptEtcCpn);
		  ordrOptEtcCpnResource.add(new Link("/docs/index.html#resources-ordrOptEtcCpn-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrOptEtcCpnResource);
	 }
}
