package kr.co.paywith.pw.data.repository.od.ordrPay;

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
@RequestMapping(value = "/api/ordrPay")
@Api(value = "OrdrPayController", description = "쿠폰 API", basePath = "/api/ordrPay")
public class OrdrPayController extends CommonController {

	 @Autowired
	 OrdrPayRepository ordrPayRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrPayValidator ordrPayValidator;

	 @Autowired
	 OrdrPayService ordrPayService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrPay(
				@RequestBody @Valid OrdrPayDto ordrPayDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrPayValidator.validate(ordrPayDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrPay ordrPay = modelMapper.map(ordrPayDto, OrdrPay.class);

		  // 레코드 등록
		  OrdrPay newOrdrPay = ordrPayService.create(ordrPay);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrPayController.class).slash(newOrdrPay.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrPayResource ordrPayResource = new OrdrPayResource(newOrdrPay);
		  ordrPayResource.add(linkTo(OrdrPayController.class).withRel("query-ordrPay"));
		  ordrPayResource.add(selfLinkBuilder.withRel("update-ordrPay"));
		  ordrPayResource.add(new Link("/docs/index.html#resources-ordrPay-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrPayResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrPays(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrPay> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrPay qOrdrPay = QOrdrPay.ordrPay;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrPay.id.eq(searchForm.getId()));
		  }


		  Page<OrdrPay> page = this.ordrPayRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrPayResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrPays-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrPayController.class).withRel("create-ordrPay"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrPay(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrPay> ordrPayOptional = this.ordrPayRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrPayOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrPay ordrPay = ordrPayOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrPayResource ordrPayResource = new OrdrPayResource(ordrPay);
		  ordrPayResource.add(new Link("/docs/index.html#resources-ordrPay-get").withRel("profile"));

		  return ResponseEntity.ok(ordrPayResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrPay(@PathVariable Integer id,
											  @RequestBody @Valid OrdrPayUpdateDto ordrPayUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrPayValidator.validate(ordrPayUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrPay> ordrPayOptional = this.ordrPayRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrPayOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrPay existOrdrPay = ordrPayOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrPay saveOrdrPay = this.ordrPayService.update(ordrPayUpdateDto, existOrdrPay);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrPayResource ordrPayResource = new OrdrPayResource(saveOrdrPay);
		  ordrPayResource.add(new Link("/docs/index.html#resources-ordrPay-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrPayResource);
	 }
}
