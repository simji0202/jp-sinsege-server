package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

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
@RequestMapping(value = "/api/mrhstStaff")
@Api(value = "MrhstStaffController", description = "쿠폰 API", basePath = "/api/mrhstStaff")
public class MrhstStaffController extends CommonController {

	 @Autowired
	 MrhstStaffRepository mrhstStaffRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 MrhstStaffValidator mrhstStaffValidator;

	 @Autowired
	 MrhstStaffService mrhstStaffService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createMrhstStaff(
				@RequestBody @Valid MrhstStaffDto mrhstStaffDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  mrhstStaffValidator.validate(mrhstStaffDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  MrhstStaff mrhstStaff = modelMapper.map(mrhstStaffDto, MrhstStaff.class);

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       mrhstStaff.setCreateBy(currentUser.getAccountId());
       mrhstStaff.setUpdateBy(currentUser.getAccountId());
     }

		  // 레코드 등록
		  MrhstStaff newMrhstStaff = mrhstStaffService.create(mrhstStaff);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(MrhstStaffController.class).slash(newMrhstStaff.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstStaffResource mrhstStaffResource = new MrhstStaffResource(newMrhstStaff);
		  mrhstStaffResource.add(linkTo(MrhstStaffController.class).withRel("query-mrhstStaff"));
		  mrhstStaffResource.add(selfLinkBuilder.withRel("update-mrhstStaff"));
		  mrhstStaffResource.add(new Link("/docs/index.html#resources-mrhstStaff-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(mrhstStaffResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getMrhstStaffs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<MrhstStaff> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QMrhstStaff qMrhstStaff = QMrhstStaff.mrhstStaff;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qMrhstStaff.id.eq(searchForm.getId()));
		  }

     booleanBuilder.and(qMrhstStaff.mrhstId.eq(searchForm.getMrhstId()));

		  Page<MrhstStaff> page = this.mrhstStaffRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new MrhstStaffResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-mrhstStaffs-list").withRel("profile"));
		  pagedResources.add(linkTo(MrhstStaffController.class).withRel("create-mrhstStaff"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getMrhstStaff(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<MrhstStaff> mrhstStaffOptional = this.mrhstStaffRepository.findById(id);

		  // 고객 정보 체크
		  if (mrhstStaffOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  MrhstStaff mrhstStaff = mrhstStaffOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstStaffResource mrhstStaffResource = new MrhstStaffResource(mrhstStaff);
		  mrhstStaffResource.add(new Link("/docs/index.html#resources-mrhstStaff-get").withRel("profile"));

		  return ResponseEntity.ok(mrhstStaffResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putMrhstStaff(@PathVariable Integer id,
											  @RequestBody @Valid MrhstStaffUpdateDto mrhstStaffUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.mrhstStaffValidator.validate(mrhstStaffUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<MrhstStaff> mrhstStaffOptional = this.mrhstStaffRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (mrhstStaffOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  MrhstStaff existMrhstStaff = mrhstStaffOptional.get();

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       existMrhstStaff.setUpdateBy(currentUser.getAccountId());
     }


     // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  MrhstStaff saveMrhstStaff = this.mrhstStaffService.update(mrhstStaffUpdateDto, existMrhstStaff);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstStaffResource mrhstStaffResource = new MrhstStaffResource(saveMrhstStaff);
		  mrhstStaffResource.add(new Link("/docs/index.html#resources-mrhstStaff-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(mrhstStaffResource);
	 }
}
