package kr.co.paywith.pw.data.repository.mbs.refund;

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
@RequestMapping(value = "/api/refund")
@Api(value = "RefundController", description = "쿠폰 API", basePath = "/api/refund")
public class RefundController extends CommonController {

	 @Autowired
	 RefundRepository refundRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 RefundValidator refundValidator;

	 @Autowired
	 RefundService refundService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createRefund(
				@RequestBody @Valid RefundDto refundDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  refundValidator.validate(refundDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Refund refund = modelMapper.map(refundDto, Refund.class);

		  // 레코드 등록
		  Refund newRefund = refundService.create(refund);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(RefundController.class).slash(newRefund.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  RefundResource refundResource = new RefundResource(newRefund);
		  refundResource.add(linkTo(RefundController.class).withRel("query-refund"));
		  refundResource.add(selfLinkBuilder.withRel("update-refund"));
		  refundResource.add(new Link("/docs/index.html#resources-refund-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(refundResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getRefunds(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Refund> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QRefund qRefund = QRefund.refund;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qRefund.id.eq(searchForm.getId()));
		  }


		  Page<Refund> page = this.refundRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new RefundResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-refunds-list").withRel("profile"));
		  pagedResources.add(linkTo(RefundController.class).withRel("create-refund"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getRefund(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Refund> refundOptional = this.refundRepository.findById(id);

		  // 고객 정보 체크
		  if (refundOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Refund refund = refundOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  RefundResource refundResource = new RefundResource(refund);
		  refundResource.add(new Link("/docs/index.html#resources-refund-get").withRel("profile"));

		  return ResponseEntity.ok(refundResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putRefund(@PathVariable Integer id,
											  @RequestBody @Valid RefundUpdateDto refundUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.refundValidator.validate(refundUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Refund> refundOptional = this.refundRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (refundOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Refund existRefund = refundOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Refund saveRefund = this.refundService.update(refundUpdateDto, existRefund);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  RefundResource refundResource = new RefundResource(saveRefund);
		  refundResource.add(new Link("/docs/index.html#resources-refund-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(refundResource);
	 }
}
