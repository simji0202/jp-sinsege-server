package kr.co.paywith.pw.data.repository.user.userCard;

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
@RequestMapping(value = "/api/userCard")
@Api(value = "UserCardController", description = "쿠폰 API", basePath = "/api/userCard")
public class UserCardController extends CommonController {

	 @Autowired
	 UserCardRepository userCardRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 UserCardValidator userCardValidator;

	 @Autowired
	 UserCardService userCardService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createUserCard(
				@RequestBody @Valid UserCardDto userCardDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  userCardValidator.validate(userCardDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  UserCard userCard = modelMapper.map(userCardDto, UserCard.class);

		  // 레코드 등록
		  UserCard newUserCard = userCardService.create(userCard);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(UserCardController.class).slash(newUserCard.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserCardResource userCardResource = new UserCardResource(newUserCard);
		  userCardResource.add(linkTo(UserCardController.class).withRel("query-userCard"));
		  userCardResource.add(selfLinkBuilder.withRel("update-userCard"));
		  userCardResource.add(new Link("/docs/index.html#resources-userCard-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(userCardResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getUserCards(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<UserCard> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QUserCard qUserCard = QUserCard.userCard;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qUserCard.id.eq(searchForm.getId()));
		  }



		  Page<UserCard> page = this.userCardRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new UserCardResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-userCards-list").withRel("profile"));
		  pagedResources.add(linkTo(UserCardController.class).withRel("create-userCard"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getUserCard(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<UserCard> userCardOptional = this.userCardRepository.findById(id);

		  // 고객 정보 체크
		  if (userCardOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  UserCard userCard = userCardOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserCardResource userCardResource = new UserCardResource(userCard);
		  userCardResource.add(new Link("/docs/index.html#resources-userCard-get").withRel("profile"));

		  return ResponseEntity.ok(userCardResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putUserCard(@PathVariable Integer id,
											  @RequestBody @Valid UserCardUpdateDto userCardUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.userCardValidator.validate(userCardUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<UserCard> userCardOptional = this.userCardRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (userCardOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  UserCard existUserCard = userCardOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  UserCard saveUserCard = this.userCardService.update(userCardUpdateDto, existUserCard);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserCardResource userCardResource = new UserCardResource(saveUserCard);
		  userCardResource.add(new Link("/docs/index.html#resources-userCard-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(userCardResource);
	 }
}
