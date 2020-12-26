package kr.co.paywith.pw.data.repository.od.userAddr;

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
@RequestMapping(value = "/api/userAddr")
@Api(value = "UserAddrController", description = "쿠폰 API", basePath = "/api/userAddr")
public class UserAddrController extends CommonController {

	 @Autowired
	 UserAddrRepository userAddrRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 UserAddrValidator userAddrValidator;

	 @Autowired
	 UserAddrService userAddrService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createUserAddr(
				@RequestBody @Valid UserAddrDto userAddrDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  userAddrValidator.validate(userAddrDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  UserAddr userAddr = modelMapper.map(userAddrDto, UserAddr.class);

		  // 레코드 등록
		  UserAddr newUserAddr = userAddrService.create(userAddr);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(UserAddrController.class).slash(newUserAddr.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserAddrResource userAddrResource = new UserAddrResource(newUserAddr);
		  userAddrResource.add(linkTo(UserAddrController.class).withRel("query-userAddr"));
		  userAddrResource.add(selfLinkBuilder.withRel("update-userAddr"));
		  userAddrResource.add(new Link("/docs/index.html#resources-userAddr-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(userAddrResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getUserAddrs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<UserAddr> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QUserAddr qUserAddr = QUserAddr.userAddr;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qUserAddr.id.eq(searchForm.getId()));
		  }


		  Page<UserAddr> page = this.userAddrRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new UserAddrResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-userAddrs-list").withRel("profile"));
		  pagedResources.add(linkTo(UserAddrController.class).withRel("create-userAddr"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getUserAddr(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<UserAddr> userAddrOptional = this.userAddrRepository.findById(id);

		  // 고객 정보 체크
		  if (userAddrOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  UserAddr userAddr = userAddrOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserAddrResource userAddrResource = new UserAddrResource(userAddr);
		  userAddrResource.add(new Link("/docs/index.html#resources-userAddr-get").withRel("profile"));

		  return ResponseEntity.ok(userAddrResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putUserAddr(@PathVariable Integer id,
											  @RequestBody @Valid UserAddrUpdateDto userAddrUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.userAddrValidator.validate(userAddrUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<UserAddr> userAddrOptional = this.userAddrRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (userAddrOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  UserAddr existUserAddr = userAddrOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  UserAddr saveUserAddr = this.userAddrService.update(userAddrUpdateDto, existUserAddr);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserAddrResource userAddrResource = new UserAddrResource(saveUserAddr);
		  userAddrResource.add(new Link("/docs/index.html#resources-userAddr-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(userAddrResource);
	 }
}
