package kr.co.paywith.pw.data.repository.od.userMrhst;

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
@RequestMapping(value = "/api/userMrhst")
@Api(value = "UserMrhstController", description = "쿠폰 API", basePath = "/api/userMrhst")
public class UserMrhstController extends CommonController {

	 @Autowired
	 UserMrhstRepository userMrhstRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 UserMrhstValidator userMrhstValidator;

	 @Autowired
	 UserMrhstService userMrhstService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createUserMrhst(
				@RequestBody @Valid UserMrhstDto userMrhstDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  userMrhstValidator.validate(userMrhstDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  UserMrhst userMrhst = modelMapper.map(userMrhstDto, UserMrhst.class);

		  // 레코드 등록
		  UserMrhst newUserMrhst = userMrhstService.create(userMrhst);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(UserMrhstController.class).slash(newUserMrhst.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserMrhstResource userMrhstResource = new UserMrhstResource(newUserMrhst);
		  userMrhstResource.add(linkTo(UserMrhstController.class).withRel("query-userMrhst"));
		  userMrhstResource.add(selfLinkBuilder.withRel("update-userMrhst"));
		  userMrhstResource.add(new Link("/docs/index.html#resources-userMrhst-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(userMrhstResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getUserMrhsts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<UserMrhst> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QUserMrhst qUserMrhst = QUserMrhst.userMrhst;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qUserMrhst.id.eq(searchForm.getId()));
		  }


		  Page<UserMrhst> page = this.userMrhstRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new UserMrhstResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-userMrhsts-list").withRel("profile"));
		  pagedResources.add(linkTo(UserMrhstController.class).withRel("create-userMrhst"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getUserMrhst(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<UserMrhst> userMrhstOptional = this.userMrhstRepository.findById(id);

		  // 고객 정보 체크
		  if (userMrhstOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  UserMrhst userMrhst = userMrhstOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserMrhstResource userMrhstResource = new UserMrhstResource(userMrhst);
		  userMrhstResource.add(new Link("/docs/index.html#resources-userMrhst-get").withRel("profile"));

		  return ResponseEntity.ok(userMrhstResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putUserMrhst(@PathVariable Integer id,
											  @RequestBody @Valid UserMrhstUpdateDto userMrhstUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.userMrhstValidator.validate(userMrhstUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<UserMrhst> userMrhstOptional = this.userMrhstRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (userMrhstOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  UserMrhst existUserMrhst = userMrhstOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  UserMrhst saveUserMrhst = this.userMrhstService.update(userMrhstUpdateDto, existUserMrhst);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserMrhstResource userMrhstResource = new UserMrhstResource(saveUserMrhst);
		  userMrhstResource.add(new Link("/docs/index.html#resources-userMrhst-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(userMrhstResource);
	 }
}
