package kr.co.paywith.pw.data.repository.od.rsrvRefund;

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
@RequestMapping(value = "/api/rsrvRefund")
@Api(value = "RsrvRefundController", description = "쿠폰 API", basePath = "/api/rsrvRefund")
public class RsrvRefundController extends CommonController {

	 @Autowired
	 RsrvRefundRepository rsrvRefundRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 RsrvRefundValidator rsrvRefundValidator;

	 @Autowired
	 RsrvRefundService rsrvRefundService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createRsrvRefund(
				@RequestBody @Valid RsrvRefundDto rsrvRefundDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  rsrvRefundValidator.validate(rsrvRefundDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  RsrvRefund rsrvRefund = modelMapper.map(rsrvRefundDto, RsrvRefund.class);

		  // 레코드 등록
		  RsrvRefund newRsrvRefund = rsrvRefundService.create(rsrvRefund);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(RsrvRefundController.class).slash(newRsrvRefund.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  RsrvRefundResource rsrvRefundResource = new RsrvRefundResource(newRsrvRefund);
		  rsrvRefundResource.add(linkTo(RsrvRefundController.class).withRel("query-rsrvRefund"));
		  rsrvRefundResource.add(selfLinkBuilder.withRel("update-rsrvRefund"));
		  rsrvRefundResource.add(new Link("/docs/index.html#resources-rsrvRefund-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(rsrvRefundResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getRsrvRefunds(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<RsrvRefund> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QRsrvRefund qRsrvRefund = QRsrvRefund.rsrvRefund;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qRsrvRefund.id.eq(searchForm.getId()));
		  }


		  Page<RsrvRefund> page = this.rsrvRefundRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new RsrvRefundResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-rsrvRefunds-list").withRel("profile"));
		  pagedResources.add(linkTo(RsrvRefundController.class).withRel("create-rsrvRefund"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getRsrvRefund(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<RsrvRefund> rsrvRefundOptional = this.rsrvRefundRepository.findById(id);

		  // 고객 정보 체크
		  if (rsrvRefundOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  RsrvRefund rsrvRefund = rsrvRefundOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  RsrvRefundResource rsrvRefundResource = new RsrvRefundResource(rsrvRefund);
		  rsrvRefundResource.add(new Link("/docs/index.html#resources-rsrvRefund-get").withRel("profile"));

		  return ResponseEntity.ok(rsrvRefundResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putRsrvRefund(@PathVariable Integer id,
											  @RequestBody @Valid RsrvRefundUpdateDto rsrvRefundUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.rsrvRefundValidator.validate(rsrvRefundUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<RsrvRefund> rsrvRefundOptional = this.rsrvRefundRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (rsrvRefundOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  RsrvRefund existRsrvRefund = rsrvRefundOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  RsrvRefund saveRsrvRefund = this.rsrvRefundService.update(rsrvRefundUpdateDto, existRsrvRefund);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  RsrvRefundResource rsrvRefundResource = new RsrvRefundResource(saveRsrvRefund);
		  rsrvRefundResource.add(new Link("/docs/index.html#resources-rsrvRefund-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(rsrvRefundResource);
	 }
}
