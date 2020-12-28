package kr.co.paywith.pw.data.repository.od.ordrGoodsCpn;

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
@RequestMapping(value = "/api/ordrGoodsCpn")
@Api(value = "OrdrGoodsCpnController", description = "쿠폰 API", basePath = "/api/ordrGoodsCpn")
public class OrdrGoodsCpnController extends CommonController {

	 @Autowired
	 OrdrGoodsCpnRepository ordrGoodsCpnRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrGoodsCpnValidator ordrGoodsCpnValidator;

	 @Autowired
	 OrdrGoodsCpnService ordrGoodsCpnService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrGoodsCpn(
				@RequestBody @Valid OrdrGoodsCpnDto ordrGoodsCpnDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrGoodsCpnValidator.validate(ordrGoodsCpnDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrGoodsCpn ordrGoodsCpn = modelMapper.map(ordrGoodsCpnDto, OrdrGoodsCpn.class);

		  // 레코드 등록
		  OrdrGoodsCpn newOrdrGoodsCpn = ordrGoodsCpnService.create(ordrGoodsCpn);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrGoodsCpnController.class).slash(newOrdrGoodsCpn.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsCpnResource ordrGoodsCpnResource = new OrdrGoodsCpnResource(newOrdrGoodsCpn);
		  ordrGoodsCpnResource.add(linkTo(OrdrGoodsCpnController.class).withRel("query-ordrGoodsCpn"));
		  ordrGoodsCpnResource.add(selfLinkBuilder.withRel("update-ordrGoodsCpn"));
		  ordrGoodsCpnResource.add(new Link("/docs/index.html#resources-ordrGoodsCpn-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrGoodsCpnResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrGoodsCpns(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrGoodsCpn> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrGoodsCpn qOrdrGoodsCpn = QOrdrGoodsCpn.ordrGoodsCpn;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrGoodsCpn.id.eq(searchForm.getId()));
		  }


		  Page<OrdrGoodsCpn> page = this.ordrGoodsCpnRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrGoodsCpnResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrGoodsCpns-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrGoodsCpnController.class).withRel("create-ordrGoodsCpn"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrGoodsCpn(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrGoodsCpn> ordrGoodsCpnOptional = this.ordrGoodsCpnRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrGoodsCpnOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrGoodsCpn ordrGoodsCpn = ordrGoodsCpnOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsCpnResource ordrGoodsCpnResource = new OrdrGoodsCpnResource(ordrGoodsCpn);
		  ordrGoodsCpnResource.add(new Link("/docs/index.html#resources-ordrGoodsCpn-get").withRel("profile"));

		  return ResponseEntity.ok(ordrGoodsCpnResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrGoodsCpn(@PathVariable Integer id,
											  @RequestBody @Valid OrdrGoodsCpnUpdateDto ordrGoodsCpnUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrGoodsCpnValidator.validate(ordrGoodsCpnUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrGoodsCpn> ordrGoodsCpnOptional = this.ordrGoodsCpnRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrGoodsCpnOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrGoodsCpn existOrdrGoodsCpn = ordrGoodsCpnOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrGoodsCpn saveOrdrGoodsCpn = this.ordrGoodsCpnService.update(ordrGoodsCpnUpdateDto, existOrdrGoodsCpn);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrGoodsCpnResource ordrGoodsCpnResource = new OrdrGoodsCpnResource(saveOrdrGoodsCpn);
		  ordrGoodsCpnResource.add(new Link("/docs/index.html#resources-ordrGoodsCpn-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrGoodsCpnResource);
	 }
}
