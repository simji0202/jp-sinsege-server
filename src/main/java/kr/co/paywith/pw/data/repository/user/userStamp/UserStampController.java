package kr.co.paywith.pw.data.repository.user.userStamp;

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
@RequestMapping(value = "/api/userStamp")
@Api(value = "UserStampController", description = "쿠폰 API", basePath = "/api/userStamp")
public class UserStampController extends CommonController {

	 @Autowired
	 UserStampRepository userStampRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 UserStampValidator userStampValidator;

	 @Autowired
	 UserStampService userStampService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createUserStamp(
				@RequestBody @Valid UserStampDto userStampDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  userStampValidator.validate(userStampDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  UserStamp userStamp = modelMapper.map(userStampDto, UserStamp.class);

		  // 레코드 등록
		  UserStamp newUserStamp = userStampService.create(userStamp);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(UserStampController.class).slash(newUserStamp.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserStampResource userStampResource = new UserStampResource(newUserStamp);
		  userStampResource.add(linkTo(UserStampController.class).withRel("query-userStamp"));
		  userStampResource.add(selfLinkBuilder.withRel("update-userStamp"));
		  userStampResource.add(new Link("/docs/index.html#resources-userStamp-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(userStampResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getUserStamps(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<UserStamp> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QUserStamp qUserStamp = QUserStamp.userStamp;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qUserStamp.id.eq(searchForm.getId()));
		  }


		  Page<UserStamp> page = this.userStampRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new UserStampResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-userStamps-list").withRel("profile"));
		  pagedResources.add(linkTo(UserStampController.class).withRel("create-userStamp"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getUserStamp(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<UserStamp> userStampOptional = this.userStampRepository.findById(id);

		  // 고객 정보 체크
		  if (userStampOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  UserStamp userStamp = userStampOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserStampResource userStampResource = new UserStampResource(userStamp);
		  userStampResource.add(new Link("/docs/index.html#resources-userStamp-get").withRel("profile"));

		  return ResponseEntity.ok(userStampResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putUserStamp(@PathVariable Integer id,
											  @RequestBody @Valid UserStampUpdateDto userStampUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.userStampValidator.validate(userStampUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<UserStamp> userStampOptional = this.userStampRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (userStampOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  UserStamp existUserStamp = userStampOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  UserStamp saveUserStamp = this.userStampService.update(userStampUpdateDto, existUserStamp);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserStampResource userStampResource = new UserStampResource(saveUserStamp);
		  userStampResource.add(new Link("/docs/index.html#resources-userStamp-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(userStampResource);
	 }
}
