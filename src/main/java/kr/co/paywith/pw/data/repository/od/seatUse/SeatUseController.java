package kr.co.paywith.pw.data.repository.od.seatUse;

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
@RequestMapping(value = "/api/seatUse")
@Api(value = "SeatUseController", description = "쿠폰 API", basePath = "/api/seatUse")
public class SeatUseController extends CommonController {

	 @Autowired
	 SeatUseRepository seatUseRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 SeatUseValidator seatUseValidator;

	 @Autowired
	 SeatUseService seatUseService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createSeatUse(
				@RequestBody @Valid SeatUseDto seatUseDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  seatUseValidator.validate(seatUseDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  SeatUse seatUse = modelMapper.map(seatUseDto, SeatUse.class);

		  // 레코드 등록
		  SeatUse newSeatUse = seatUseService.create(seatUse);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(SeatUseController.class).slash(newSeatUse.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatUseResource seatUseResource = new SeatUseResource(newSeatUse);
		  seatUseResource.add(linkTo(SeatUseController.class).withRel("query-seatUse"));
		  seatUseResource.add(selfLinkBuilder.withRel("update-seatUse"));
		  seatUseResource.add(new Link("/docs/index.html#resources-seatUse-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(seatUseResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getSeatUses(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<SeatUse> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QSeatUse qSeatUse = QSeatUse.seatUse;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qSeatUse.id.eq(searchForm.getId()));
		  }


		  Page<SeatUse> page = this.seatUseRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new SeatUseResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-seatUses-list").withRel("profile"));
		  pagedResources.add(linkTo(SeatUseController.class).withRel("create-seatUse"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getSeatUse(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<SeatUse> seatUseOptional = this.seatUseRepository.findById(id);

		  // 고객 정보 체크
		  if (seatUseOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  SeatUse seatUse = seatUseOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatUseResource seatUseResource = new SeatUseResource(seatUse);
		  seatUseResource.add(new Link("/docs/index.html#resources-seatUse-get").withRel("profile"));

		  return ResponseEntity.ok(seatUseResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putSeatUse(@PathVariable Integer id,
											  @RequestBody @Valid SeatUseUpdateDto seatUseUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.seatUseValidator.validate(seatUseUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<SeatUse> seatUseOptional = this.seatUseRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (seatUseOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  SeatUse existSeatUse = seatUseOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  SeatUse saveSeatUse = this.seatUseService.update(seatUseUpdateDto, existSeatUse);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatUseResource seatUseResource = new SeatUseResource(saveSeatUse);
		  seatUseResource.add(new Link("/docs/index.html#resources-seatUse-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(seatUseResource);
	 }
}
