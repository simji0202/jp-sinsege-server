package kr.co.paywith.pw.data.repository.od.seatSch;

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
@RequestMapping(value = "/api/seatSch")
@Api(value = "SeatSchController", description = "쿠폰 API", basePath = "/api/seatSch")
public class SeatSchController extends CommonController {

	 @Autowired
	 SeatSchRepository seatSchRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 SeatSchValidator seatSchValidator;

	 @Autowired
	 SeatSchService seatSchService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createSeatSch(
				@RequestBody @Valid SeatSchDto seatSchDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  seatSchValidator.validate(seatSchDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  SeatSch seatSch = modelMapper.map(seatSchDto, SeatSch.class);

		  // 레코드 등록
		  SeatSch newSeatSch = seatSchService.create(seatSch);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(SeatSchController.class).slash(newSeatSch.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatSchResource seatSchResource = new SeatSchResource(newSeatSch);
		  seatSchResource.add(linkTo(SeatSchController.class).withRel("query-seatSch"));
		  seatSchResource.add(selfLinkBuilder.withRel("update-seatSch"));
		  seatSchResource.add(new Link("/docs/index.html#resources-seatSch-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(seatSchResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getSeatSchs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<SeatSch> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QSeatSch qSeatSch = QSeatSch.seatSch;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qSeatSch.id.eq(searchForm.getId()));
		  }


		  Page<SeatSch> page = this.seatSchRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new SeatSchResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-seatSchs-list").withRel("profile"));
		  pagedResources.add(linkTo(SeatSchController.class).withRel("create-seatSch"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getSeatSch(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<SeatSch> seatSchOptional = this.seatSchRepository.findById(id);

		  // 고객 정보 체크
		  if (seatSchOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  SeatSch seatSch = seatSchOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatSchResource seatSchResource = new SeatSchResource(seatSch);
		  seatSchResource.add(new Link("/docs/index.html#resources-seatSch-get").withRel("profile"));

		  return ResponseEntity.ok(seatSchResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putSeatSch(@PathVariable Integer id,
											  @RequestBody @Valid SeatSchUpdateDto seatSchUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.seatSchValidator.validate(seatSchUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<SeatSch> seatSchOptional = this.seatSchRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (seatSchOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  SeatSch existSeatSch = seatSchOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  SeatSch saveSeatSch = this.seatSchService.update(seatSchUpdateDto, existSeatSch);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatSchResource seatSchResource = new SeatSchResource(saveSeatSch);
		  seatSchResource.add(new Link("/docs/index.html#resources-seatSch-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(seatSchResource);
	 }
}
