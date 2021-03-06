package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

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
@RequestMapping(value = "/api/delngOrdrSeatTimetable")
@Api(value = "DelngOrdrSeatTimetableController", description = "쿠폰 API", basePath = "/api/delngOrdrSeatTimetable")
public class DelngOrdrSeatTimetableController extends CommonController {

	 @Autowired
	 DelngOrdrSeatTimetableRepository delngOrdrSeatTimetableRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 DelngOrdrSeatTimetableValidator delngOrdrSeatTimetableValidator;

	 @Autowired
	 DelngOrdrSeatTimetableService delngOrdrSeatTimetableService;

//	 /**
//	  * 정보 등록
//	  */
//	 @PostMapping
//	 public ResponseEntity createDelngOrdrSeatTimetable(
//				@RequestBody @Valid DelngOrdrSeatTimetableDto delngOrdrSeatTimetableDto,
//				Errors errors,
//				@CurrentUser Account currentUser) {
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값 체크
//		  delngOrdrSeatTimetableValidator.validate(delngOrdrSeatTimetableDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값을 브랜드 객채에 대입
//		  DelngOrdrSeatTimetable delngOrdrSeatTimetable = modelMapper.map(delngOrdrSeatTimetableDto, DelngOrdrSeatTimetable.class);
//
//		  // 레코드 등록
//		  DelngOrdrSeatTimetable newDelngOrdrSeatTimetable = delngOrdrSeatTimetableService.create(delngOrdrSeatTimetable);
//
//		  ControllerLinkBuilder selfLinkBuilder = linkTo(DelngOrdrSeatTimetableController.class).slash(newDelngOrdrSeatTimetable.getId());
//
//		  URI createdUri = selfLinkBuilder.toUri();
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  DelngOrdrSeatTimetableResource delngOrdrSeatTimetableResource = new DelngOrdrSeatTimetableResource(newDelngOrdrSeatTimetable);
//		  delngOrdrSeatTimetableResource.add(linkTo(DelngOrdrSeatTimetableController.class).withRel("query-delngOrdrSeatTimetable"));
//		  delngOrdrSeatTimetableResource.add(selfLinkBuilder.withRel("update-delngOrdrSeatTimetable"));
//		  delngOrdrSeatTimetableResource.add(new Link("/docs/index.html#resources-delngOrdrSeatTimetable-create").withRel("profile"));
//
//		  return ResponseEntity.created(createdUri).body(delngOrdrSeatTimetableResource);
//	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getDelngOrdrSeatTimetables(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<DelngOrdrSeatTimetable> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QDelngOrdrSeatTimetable qDelngOrdrSeatTimetable = QDelngOrdrSeatTimetable.delngOrdrSeatTimetable;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qDelngOrdrSeatTimetable.id.eq(searchForm.getId()));
		  }


		  Page<DelngOrdrSeatTimetable> page = this.delngOrdrSeatTimetableRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new DelngOrdrSeatTimetableResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-delngOrdrSeatTimetables-list").withRel("profile"));
		  pagedResources.add(linkTo(DelngOrdrSeatTimetableController.class).withRel("create-delngOrdrSeatTimetable"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getDelngOrdrSeatTimetable(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<DelngOrdrSeatTimetable> delngOrdrSeatTimetableOptional = this.delngOrdrSeatTimetableRepository.findById(id);

		  // 고객 정보 체크
		  if (delngOrdrSeatTimetableOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  DelngOrdrSeatTimetable delngOrdrSeatTimetable = delngOrdrSeatTimetableOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DelngOrdrSeatTimetableResource delngOrdrSeatTimetableResource = new DelngOrdrSeatTimetableResource(delngOrdrSeatTimetable);
		  delngOrdrSeatTimetableResource.add(new Link("/docs/index.html#resources-delngOrdrSeatTimetable-get").withRel("profile"));

		  return ResponseEntity.ok(delngOrdrSeatTimetableResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putDelngOrdrSeatTimetable(@PathVariable Integer id,
											  @RequestBody @Valid DelngOrdrSeatTimetableUpdateDto delngOrdrSeatTimetableUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.delngOrdrSeatTimetableValidator.validate(delngOrdrSeatTimetableUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<DelngOrdrSeatTimetable> delngOrdrSeatTimetableOptional = this.delngOrdrSeatTimetableRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (delngOrdrSeatTimetableOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  DelngOrdrSeatTimetable existDelngOrdrSeatTimetable = delngOrdrSeatTimetableOptional.get();

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       existDelngOrdrSeatTimetable.setUpdateBy(currentUser.getAccountId());
     }

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  DelngOrdrSeatTimetable saveDelngOrdrSeatTimetable = this.delngOrdrSeatTimetableService.update(delngOrdrSeatTimetableUpdateDto, existDelngOrdrSeatTimetable);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DelngOrdrSeatTimetableResource delngOrdrSeatTimetableResource = new DelngOrdrSeatTimetableResource(saveDelngOrdrSeatTimetable);
		  delngOrdrSeatTimetableResource.add(new Link("/docs/index.html#resources-delngOrdrSeatTimetable-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(delngOrdrSeatTimetableResource);
	 }
}
