package kr.co.paywith.pw.data.repository.od.seat;

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
@RequestMapping(value = "/api/seat")
@Api(value = "SeatController", description = "쿠폰 API", basePath = "/api/seat")
public class SeatController extends CommonController {

	 @Autowired
	 SeatRepository seatRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 SeatValidator seatValidator;

	 @Autowired
	 SeatService seatService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createSeat(
				@RequestBody @Valid SeatDto seatDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  seatValidator.validate(seatDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Seat seat = modelMapper.map(seatDto, Seat.class);

		  // 레코드 등록
		  Seat newSeat = seatService.create(seat);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(SeatController.class).slash(newSeat.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatResource seatResource = new SeatResource(newSeat);
		  seatResource.add(linkTo(SeatController.class).withRel("query-seat"));
		  seatResource.add(selfLinkBuilder.withRel("update-seat"));
		  seatResource.add(new Link("/docs/index.html#resources-seat-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(seatResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getSeats(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Seat> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QSeat qSeat = QSeat.seat;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qSeat.id.eq(searchForm.getId()));
		  }


		  Page<Seat> page = this.seatRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new SeatResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-seats-list").withRel("profile"));
		  pagedResources.add(linkTo(SeatController.class).withRel("create-seat"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getSeat(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Seat> seatOptional = this.seatRepository.findById(id);

		  // 고객 정보 체크
		  if (seatOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Seat seat = seatOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatResource seatResource = new SeatResource(seat);
		  seatResource.add(new Link("/docs/index.html#resources-seat-get").withRel("profile"));

		  return ResponseEntity.ok(seatResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putSeat(@PathVariable Integer id,
											  @RequestBody @Valid SeatUpdateDto seatUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.seatValidator.validate(seatUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Seat> seatOptional = this.seatRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (seatOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Seat existSeat = seatOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Seat saveSeat = this.seatService.update(seatUpdateDto, existSeat);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatResource seatResource = new SeatResource(saveSeat);
		  seatResource.add(new Link("/docs/index.html#resources-seat-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(seatResource);
	 }
}
