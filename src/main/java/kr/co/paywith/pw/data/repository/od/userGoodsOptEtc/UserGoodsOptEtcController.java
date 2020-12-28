package kr.co.paywith.pw.data.repository.od.userGoodsOptEtc;

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
@RequestMapping(value = "/api/userGoodsOptEtc")
@Api(value = "UserGoodsOptEtcController", description = "쿠폰 API", basePath = "/api/userGoodsOptEtc")
public class UserGoodsOptEtcController extends CommonController {

	 @Autowired
	 UserGoodsOptEtcRepository userGoodsOptEtcRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 UserGoodsOptEtcValidator userGoodsOptEtcValidator;

	 @Autowired
	 UserGoodsOptEtcService userGoodsOptEtcService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createUserGoodsOptEtc(
				@RequestBody @Valid UserGoodsOptEtcDto userGoodsOptEtcDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  userGoodsOptEtcValidator.validate(userGoodsOptEtcDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  UserGoodsOptEtc userGoodsOptEtc = modelMapper.map(userGoodsOptEtcDto, UserGoodsOptEtc.class);

		  // 레코드 등록
		  UserGoodsOptEtc newUserGoodsOptEtc = userGoodsOptEtcService.create(userGoodsOptEtc);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(UserGoodsOptEtcController.class).slash(newUserGoodsOptEtc.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserGoodsOptEtcResource userGoodsOptEtcResource = new UserGoodsOptEtcResource(newUserGoodsOptEtc);
		  userGoodsOptEtcResource.add(linkTo(UserGoodsOptEtcController.class).withRel("query-userGoodsOptEtc"));
		  userGoodsOptEtcResource.add(selfLinkBuilder.withRel("update-userGoodsOptEtc"));
		  userGoodsOptEtcResource.add(new Link("/docs/index.html#resources-userGoodsOptEtc-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(userGoodsOptEtcResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getUserGoodsOptEtcs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<UserGoodsOptEtc> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QUserGoodsOptEtc qUserGoodsOptEtc = QUserGoodsOptEtc.userGoodsOptEtc;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qUserGoodsOptEtc.id.eq(searchForm.getId()));
		  }


		  Page<UserGoodsOptEtc> page = this.userGoodsOptEtcRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new UserGoodsOptEtcResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-userGoodsOptEtcs-list").withRel("profile"));
		  pagedResources.add(linkTo(UserGoodsOptEtcController.class).withRel("create-userGoodsOptEtc"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getUserGoodsOptEtc(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<UserGoodsOptEtc> userGoodsOptEtcOptional = this.userGoodsOptEtcRepository.findById(id);

		  // 고객 정보 체크
		  if (userGoodsOptEtcOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  UserGoodsOptEtc userGoodsOptEtc = userGoodsOptEtcOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserGoodsOptEtcResource userGoodsOptEtcResource = new UserGoodsOptEtcResource(userGoodsOptEtc);
		  userGoodsOptEtcResource.add(new Link("/docs/index.html#resources-userGoodsOptEtc-get").withRel("profile"));

		  return ResponseEntity.ok(userGoodsOptEtcResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putUserGoodsOptEtc(@PathVariable Integer id,
											  @RequestBody @Valid UserGoodsOptEtcUpdateDto userGoodsOptEtcUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.userGoodsOptEtcValidator.validate(userGoodsOptEtcUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<UserGoodsOptEtc> userGoodsOptEtcOptional = this.userGoodsOptEtcRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (userGoodsOptEtcOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  UserGoodsOptEtc existUserGoodsOptEtc = userGoodsOptEtcOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  UserGoodsOptEtc saveUserGoodsOptEtc = this.userGoodsOptEtcService.update(userGoodsOptEtcUpdateDto, existUserGoodsOptEtc);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  UserGoodsOptEtcResource userGoodsOptEtcResource = new UserGoodsOptEtcResource(saveUserGoodsOptEtc);
		  userGoodsOptEtcResource.add(new Link("/docs/index.html#resources-userGoodsOptEtc-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(userGoodsOptEtcResource);
	 }
}
