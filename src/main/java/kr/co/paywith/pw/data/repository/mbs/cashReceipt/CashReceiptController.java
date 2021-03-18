package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

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
@RequestMapping(value = "/api/cashReceipt")
@Api(value = "CashReceiptController", description = "쿠폰 API", basePath = "/api/cashReceipt")
public class CashReceiptController extends CommonController {

	 @Autowired
	 CashReceiptRepository cashReceiptRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 CashReceiptValidator cashReceiptValidator;

	 @Autowired
	 CashReceiptService cashReceiptService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createCashReceipt(
				@RequestBody @Valid CashReceiptDto cashReceiptDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  cashReceiptValidator.validate(cashReceiptDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  CashReceipt cashReceipt = modelMapper.map(cashReceiptDto, CashReceipt.class);

		  // 레코드 등록
		  CashReceipt newCashReceipt = cashReceiptService.create(cashReceipt);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(CashReceiptController.class).slash(newCashReceipt.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CashReceiptResource cashReceiptResource = new CashReceiptResource(newCashReceipt);
		  cashReceiptResource.add(linkTo(CashReceiptController.class).withRel("query-cashReceipt"));
		  cashReceiptResource.add(selfLinkBuilder.withRel("update-cashReceipt"));
		  cashReceiptResource.add(new Link("/docs/index.html#resources-cashReceipt-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(cashReceiptResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getCashReceipts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<CashReceipt> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QCashReceipt qCashReceipt = QCashReceipt.cashReceipt;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qCashReceipt.id.eq(searchForm.getId()));
		  }


		  Page<CashReceipt> page = this.cashReceiptRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new CashReceiptResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-cashReceipts-list").withRel("profile"));
		  pagedResources.add(linkTo(CashReceiptController.class).withRel("create-cashReceipt"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getCashReceipt(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<CashReceipt> cashReceiptOptional = this.cashReceiptRepository.findById(id);

		  // 고객 정보 체크
		  if (cashReceiptOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  CashReceipt cashReceipt = cashReceiptOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CashReceiptResource cashReceiptResource = new CashReceiptResource(cashReceipt);
		  cashReceiptResource.add(new Link("/docs/index.html#resources-cashReceipt-get").withRel("profile"));

		  return ResponseEntity.ok(cashReceiptResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putCashReceipt(@PathVariable Integer id,
											  @RequestBody @Valid CashReceiptUpdateDto cashReceiptUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.cashReceiptValidator.validate(cashReceiptUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<CashReceipt> cashReceiptOptional = this.cashReceiptRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (cashReceiptOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  CashReceipt existCashReceipt = cashReceiptOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  CashReceipt saveCashReceipt = this.cashReceiptService.update(cashReceiptUpdateDto, existCashReceipt);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CashReceiptResource cashReceiptResource = new CashReceiptResource(saveCashReceipt);
		  cashReceiptResource.add(new Link("/docs/index.html#resources-cashReceipt-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(cashReceiptResource);
	 }
}
