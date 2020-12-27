package kr.co.paywith.pw.data.repository.mbs.notifUser;

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
@RequestMapping(value = "/api/notifUser")
@Api(value = "NotifUserController", description = "쿠폰 API", basePath = "/api/notifUser")
public class NotifUserController extends CommonController {

	 @Autowired
	 NotifUserRepository notifUserRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 NotifUserValidator notifUserValidator;

	 @Autowired
	 NotifUserService notifUserService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createNotifUser(
				@RequestBody @Valid NotifUserDto notifUserDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  notifUserValidator.validate(notifUserDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  NotifUser notifUser = modelMapper.map(notifUserDto, NotifUser.class);

		  // 레코드 등록
		  NotifUser newNotifUser = notifUserService.create(notifUser);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(NotifUserController.class).slash(newNotifUser.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  NotifUserResource notifUserResource = new NotifUserResource(newNotifUser);
		  notifUserResource.add(linkTo(NotifUserController.class).withRel("query-notifUser"));
		  notifUserResource.add(selfLinkBuilder.withRel("update-notifUser"));
		  notifUserResource.add(new Link("/docs/index.html#resources-notifUser-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(notifUserResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getNotifUsers(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<NotifUser> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QNotifUser qNotifUser = QNotifUser.notifUser;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qNotifUser.id.eq(searchForm.getId()));
		  }


		  Page<NotifUser> page = this.notifUserRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new NotifUserResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-notifUsers-list").withRel("profile"));
		  pagedResources.add(linkTo(NotifUserController.class).withRel("create-notifUser"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getNotifUser(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<NotifUser> notifUserOptional = this.notifUserRepository.findById(id);

		  // 고객 정보 체크
		  if (notifUserOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  NotifUser notifUser = notifUserOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  NotifUserResource notifUserResource = new NotifUserResource(notifUser);
		  notifUserResource.add(new Link("/docs/index.html#resources-notifUser-get").withRel("profile"));

		  return ResponseEntity.ok(notifUserResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putNotifUser(@PathVariable Integer id,
											  @RequestBody @Valid NotifUserUpdateDto notifUserUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.notifUserValidator.validate(notifUserUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<NotifUser> notifUserOptional = this.notifUserRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (notifUserOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  NotifUser existNotifUser = notifUserOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  NotifUser saveNotifUser = this.notifUserService.update(notifUserUpdateDto, existNotifUser);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  NotifUserResource notifUserResource = new NotifUserResource(saveNotifUser);
		  notifUserResource.add(new Link("/docs/index.html#resources-notifUser-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(notifUserResource);
	 }
}
