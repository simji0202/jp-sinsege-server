package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import kr.co.paywith.pw.component.TimetableService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/seatTimetable")
@Api(value = "SeatTimetableController", description = "좌석 시간표 API", basePath = "/api/seatTimetable")
public class SeatTimetableController extends CommonController {

	 @Autowired
	 SeatTimetableRepository seatTimetableRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 SeatTimetableValidator seatTimetableValidator;

	 @Autowired
	 SeatTimetableService seatTimetableService;

//	 /**
//	  * 정보 등록
//	  */
//	 @PostMapping
//	 public ResponseEntity createSeatTimetable(
//				@RequestBody @Valid SeatTimetableDto seatTimetableDto,
//				Errors errors,
//				@CurrentUser Account currentUser) {
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값 체크
//		  seatTimetableValidator.validate(seatTimetableDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값을 브랜드 객채에 대입
//		  SeatTimetable seatTimetable = modelMapper.map(seatTimetableDto, SeatTimetable.class);
//
//		  // 레코드 등록
//		  SeatTimetable newSeatTimetable = seatTimetableService.create(seatTimetable);
//
//		  ControllerLinkBuilder selfLinkBuilder = linkTo(SeatTimetableController.class).slash(newSeatTimetable.getId());
//
//		  URI createdUri = selfLinkBuilder.toUri();
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  SeatTimetableResource seatTimetableResource = new SeatTimetableResource(newSeatTimetable);
//		  seatTimetableResource.add(linkTo(SeatTimetableController.class).withRel("query-seatTimetable"));
//		  seatTimetableResource.add(selfLinkBuilder.withRel("update-seatTimetable"));
//		  seatTimetableResource.add(new Link("/docs/index.html#resources-seatTimetable-create").withRel("profile"));
//
//		  return ResponseEntity.created(createdUri).body(seatTimetableResource);
//	 }

	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getSeatTimetables(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<SeatTimetable> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QSeatTimetable qSeatTimetable = QSeatTimetable.seatTimetable;

		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qSeatTimetable.id.eq(searchForm.getId()));
		  }

		  if (searchForm.getRangeStartDttm() != null) {
		    booleanBuilder.and(qSeatTimetable.startDttm.after(searchForm.getRangeStartDttm()));
      }

		  if (searchForm.getRangeEndDttm() != null) {
		    booleanBuilder.and(qSeatTimetable.endDttm.before(searchForm.getRangeEndDttm()));
      }

		  if (searchForm.getStaffId() != null) {
		    booleanBuilder.and(qSeatTimetable.mrhstStaff.id.eq(searchForm.getStaffId()));
      }

		  if (searchForm.getMrhstId() != null) {
		    booleanBuilder.and(qSeatTimetable.mrhstSeat.mrhstId.eq(searchForm.getStaffId()));
      }

		  Page<SeatTimetable> page = this.seatTimetableRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new SeatTimetableResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-seatTimetables-list").withRel("profile"));
		  pagedResources.add(linkTo(SeatTimetableController.class).withRel("create-seatTimetable"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getSeatTimetable(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<SeatTimetable> seatTimetableOptional = this.seatTimetableRepository.findById(id);

		  // 고객 정보 체크
		  if (seatTimetableOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  SeatTimetable seatTimetable = seatTimetableOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatTimetableResource seatTimetableResource = new SeatTimetableResource(seatTimetable);
		  seatTimetableResource.add(new Link("/docs/index.html#resources-seatTimetable-get").withRel("profile"));

		  return ResponseEntity.ok(seatTimetableResource);
	 }


	 /**
	  * 정보 변경. AVAIL - UNAVAIL 간 상태 전환에만 사용
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putSeatTimetable(@PathVariable Integer id,
											  @RequestBody @Valid SeatTimetableUpdateDto seatTimetableUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		 // 기존 테이블에서 관련 정보 취득
		 Optional<SeatTimetable> seatTimetableOptional = this.seatTimetableRepository.findById(id);

		 // 기존 정보 유무 체크
		 if (seatTimetableOptional.isEmpty()) {
			 // 404 Error return
			 return ResponseEntity.notFound().build();
		 }

		 // 기존 정보 취득
		 SeatTimetable existSeatTimetable = seatTimetableOptional.get();

		 // 논리적 오류 (제약조건) 체크
		 this.seatTimetableValidator.validate(seatTimetableUpdateDto, existSeatTimetable, errors);
		 if (errors.hasErrors()) {
			 return badRequest(errors);
		 }

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  SeatTimetable saveSeatTimetable = this.seatTimetableService.update(seatTimetableUpdateDto, existSeatTimetable);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  SeatTimetableResource seatTimetableResource = new SeatTimetableResource(saveSeatTimetable);
		  seatTimetableResource.add(new Link("/docs/index.html#resources-seatTimetable-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(seatTimetableResource);
	 }

	 @Autowired
	 private TimetableService timetableService;

	 @PostMapping("/make")
	public  void make() {
	 timetableService.makeTimetable();
	 }
}
