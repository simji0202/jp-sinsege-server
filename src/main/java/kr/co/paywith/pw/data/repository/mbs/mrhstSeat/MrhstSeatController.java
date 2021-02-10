package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

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
@RequestMapping(value = "/api/mrhstSeat")
@Api(value = "MrhstSeatController", description = "쿠폰 API", basePath = "/api/mrhstSeat")
public class MrhstSeatController extends CommonController {

	 @Autowired
	 MrhstSeatRepository mrhstSeatRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 MrhstSeatValidator mrhstSeatValidator;

	 @Autowired
	 MrhstSeatService mrhstSeatService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createMrhstSeat(
				@RequestBody @Valid MrhstSeatDto mrhstSeatDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  mrhstSeatValidator.validate(mrhstSeatDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  MrhstSeat mrhstSeat = modelMapper.map(mrhstSeatDto, MrhstSeat.class);

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       mrhstSeat.setCreateBy(currentUser.getAccountId());
       mrhstSeat.setUpdateBy(currentUser.getAccountId());
     }

		  // 레코드 등록
		  MrhstSeat newMrhstSeat = mrhstSeatService.create(mrhstSeat);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(MrhstSeatController.class).slash(newMrhstSeat.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstSeatResource mrhstSeatResource = new MrhstSeatResource(newMrhstSeat);
		  mrhstSeatResource.add(linkTo(MrhstSeatController.class).withRel("query-mrhstSeat"));
		  mrhstSeatResource.add(selfLinkBuilder.withRel("update-mrhstSeat"));
		  mrhstSeatResource.add(new Link("/docs/index.html#resources-mrhstSeat-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(mrhstSeatResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getMrhstSeats(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<MrhstSeat> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QMrhstSeat qMrhstSeat = QMrhstSeat.mrhstSeat;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qMrhstSeat.id.eq(searchForm.getId()));
		  }

		  booleanBuilder.and(
		  		searchForm.getMrhstId() != null ? qMrhstSeat.mrhstId.eq(searchForm.getMrhstId()) : null);

		  Page<MrhstSeat> page = this.mrhstSeatRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new MrhstSeatResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-mrhstSeats-list").withRel("profile"));
		  pagedResources.add(linkTo(MrhstSeatController.class).withRel("create-mrhstSeat"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getMrhstSeat(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<MrhstSeat> mrhstSeatOptional = this.mrhstSeatRepository.findById(id);

		  // 고객 정보 체크
		  if (mrhstSeatOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  MrhstSeat mrhstSeat = mrhstSeatOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstSeatResource mrhstSeatResource = new MrhstSeatResource(mrhstSeat);
		  mrhstSeatResource.add(new Link("/docs/index.html#resources-mrhstSeat-get").withRel("profile"));

		  return ResponseEntity.ok(mrhstSeatResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putMrhstSeat(@PathVariable Integer id,
											  @RequestBody @Valid MrhstSeatUpdateDto mrhstSeatUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.mrhstSeatValidator.validate(mrhstSeatUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<MrhstSeat> mrhstSeatOptional = this.mrhstSeatRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (mrhstSeatOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  MrhstSeat existMrhstSeat = mrhstSeatOptional.get();

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       existMrhstSeat.setUpdateBy(currentUser.getAccountId());
     }


     // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  MrhstSeat saveMrhstSeat = this.mrhstSeatService.update(mrhstSeatUpdateDto, existMrhstSeat);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstSeatResource mrhstSeatResource = new MrhstSeatResource(saveMrhstSeat);
		  mrhstSeatResource.add(new Link("/docs/index.html#resources-mrhstSeat-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(mrhstSeatResource);
	 }
}
