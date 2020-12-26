package kr.co.paywith.pw.data.repository.od.ordrDeliv;

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
@RequestMapping(value = "/api/ordrDeliv")
@Api(value = "OrdrDelivController", description = "쿠폰 API", basePath = "/api/ordrDeliv")
public class OrdrDelivController extends CommonController {

	 @Autowired
	 OrdrDelivRepository ordrDelivRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrDelivValidator ordrDelivValidator;

	 @Autowired
	 OrdrDelivService ordrDelivService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrDeliv(
				@RequestBody @Valid OrdrDelivDto ordrDelivDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrDelivValidator.validate(ordrDelivDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrDeliv ordrDeliv = modelMapper.map(ordrDelivDto, OrdrDeliv.class);

		  // 레코드 등록
		  OrdrDeliv newOrdrDeliv = ordrDelivService.create(ordrDeliv);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrDelivController.class).slash(newOrdrDeliv.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrDelivResource ordrDelivResource = new OrdrDelivResource(newOrdrDeliv);
		  ordrDelivResource.add(linkTo(OrdrDelivController.class).withRel("query-ordrDeliv"));
		  ordrDelivResource.add(selfLinkBuilder.withRel("update-ordrDeliv"));
		  ordrDelivResource.add(new Link("/docs/index.html#resources-ordrDeliv-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrDelivResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrDelivs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrDeliv> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrDeliv qOrdrDeliv = QOrdrDeliv.ordrDeliv;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrDeliv.id.eq(searchForm.getId()));
		  }


		  Page<OrdrDeliv> page = this.ordrDelivRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrDelivResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrDelivs-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrDelivController.class).withRel("create-ordrDeliv"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrDeliv(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrDeliv> ordrDelivOptional = this.ordrDelivRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrDelivOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrDeliv ordrDeliv = ordrDelivOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrDelivResource ordrDelivResource = new OrdrDelivResource(ordrDeliv);
		  ordrDelivResource.add(new Link("/docs/index.html#resources-ordrDeliv-get").withRel("profile"));

		  return ResponseEntity.ok(ordrDelivResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrDeliv(@PathVariable Integer id,
											  @RequestBody @Valid OrdrDelivUpdateDto ordrDelivUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrDelivValidator.validate(ordrDelivUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrDeliv> ordrDelivOptional = this.ordrDelivRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrDelivOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrDeliv existOrdrDeliv = ordrDelivOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrDeliv saveOrdrDeliv = this.ordrDelivService.update(ordrDelivUpdateDto, existOrdrDeliv);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrDelivResource ordrDelivResource = new OrdrDelivResource(saveOrdrDeliv);
		  ordrDelivResource.add(new Link("/docs/index.html#resources-ordrDeliv-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrDelivResource);
	 }
}
