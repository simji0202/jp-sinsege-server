package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
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
@RequestMapping(value = "/api/payment")
@Api(value = "PaymentController", description = "쿠폰 API", basePath = "/api/delngPayment")
public class DelngPaymentController extends CommonController {

	 @Autowired
	 DelngPaymentRepository delngPaymentRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 DelngPaymentValidator delngPaymentValidator;

	 @Autowired
	 DelngPaymentService delngPaymentService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPayment(
				@RequestBody @Valid DelngPaymentDto delngPaymentDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  delngPaymentValidator.validate(delngPaymentDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  DelngPayment delngPayment = modelMapper.map(delngPaymentDto, DelngPayment.class);

		  // 레코드 등록
		  DelngPayment newDelngPayment = delngPaymentService.create(delngPayment);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(DelngPaymentController.class).slash(newDelngPayment.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DelngPaymentResource delngPaymentResource = new DelngPaymentResource(newDelngPayment);
		  delngPaymentResource.add(linkTo(DelngPaymentController.class).withRel("query-delngPayment"));
		  delngPaymentResource.add(selfLinkBuilder.withRel("update-delngPayment"));
		  delngPaymentResource.add(new Link("/docs/index.html#resources-delngPayment-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(delngPaymentResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPayments(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<DelngPayment> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QDelngPayment qDelngPayment = QDelngPayment.delngPayment;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qDelngPayment.id.eq(searchForm.getId()));
		  }


		  Page<DelngPayment> page = this.delngPaymentRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new DelngPaymentResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-delngPayments-list").withRel("profile"));
		  pagedResources.add(linkTo(DelngPaymentController.class).withRel("create-delngPayment"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPayment(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<DelngPayment> paymentOptional = this.delngPaymentRepository.findById(id);

		  // 고객 정보 체크
		  if (paymentOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  DelngPayment delngPayment = paymentOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DelngPaymentResource delngPaymentResource = new DelngPaymentResource(delngPayment);
		  delngPaymentResource.add(new Link("/docs/index.html#resources-delngPayment-get").withRel("profile"));

		  return ResponseEntity.ok(delngPaymentResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPayment(@PathVariable Integer id,
											  @RequestBody @Valid DelngPaymentUpdateDto delngPaymentUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.delngPaymentValidator.validate(delngPaymentUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<DelngPayment> paymentOptional = this.delngPaymentRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (paymentOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  DelngPayment existDelngPayment = paymentOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  DelngPayment saveDelngPayment = this.delngPaymentService.update(delngPaymentUpdateDto,
					existDelngPayment);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DelngPaymentResource delngPaymentResource = new DelngPaymentResource(saveDelngPayment);
		  delngPaymentResource.add(new Link("/docs/index.html#resources-delngPayment-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(delngPaymentResource);
	 }
}
