package kr.co.paywith.pw.data.repository.od.staff;

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
@RequestMapping(value = "/api/staff")
@Api(value = "StaffController", description = "쿠폰 API", basePath = "/api/staff")
public class StaffController extends CommonController {

	 @Autowired
	 StaffRepository staffRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 StaffValidator staffValidator;

	 @Autowired
	 StaffService staffService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createStaff(
				@RequestBody @Valid StaffDto staffDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  staffValidator.validate(staffDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Staff staff = modelMapper.map(staffDto, Staff.class);

		  // 레코드 등록
		  Staff newStaff = staffService.create(staff);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(StaffController.class).slash(newStaff.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StaffResource staffResource = new StaffResource(newStaff);
		  staffResource.add(linkTo(StaffController.class).withRel("query-staff"));
		  staffResource.add(selfLinkBuilder.withRel("update-staff"));
		  staffResource.add(new Link("/docs/index.html#resources-staff-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(staffResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getStaffs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Staff> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QStaff qStaff = QStaff.staff;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qStaff.id.eq(searchForm.getId()));
		  }


		  Page<Staff> page = this.staffRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new StaffResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-staffs-list").withRel("profile"));
		  pagedResources.add(linkTo(StaffController.class).withRel("create-staff"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getStaff(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Staff> staffOptional = this.staffRepository.findById(id);

		  // 고객 정보 체크
		  if (staffOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Staff staff = staffOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StaffResource staffResource = new StaffResource(staff);
		  staffResource.add(new Link("/docs/index.html#resources-staff-get").withRel("profile"));

		  return ResponseEntity.ok(staffResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putStaff(@PathVariable Integer id,
											  @RequestBody @Valid StaffUpdateDto staffUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.staffValidator.validate(staffUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Staff> staffOptional = this.staffRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (staffOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Staff existStaff = staffOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Staff saveStaff = this.staffService.update(staffUpdateDto, existStaff);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StaffResource staffResource = new StaffResource(saveStaff);
		  staffResource.add(new Link("/docs/index.html#resources-staff-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(staffResource);
	 }
}
