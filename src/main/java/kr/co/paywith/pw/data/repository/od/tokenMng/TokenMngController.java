package kr.co.paywith.pw.data.repository.od.tokenMng;

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
@RequestMapping(value = "/api/tokenMng")
@Api(value = "TokenMngController", description = "쿠폰 API", basePath = "/api/tokenMng")
public class TokenMngController extends CommonController {

	 @Autowired
	 TokenMngRepository tokenMngRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 TokenMngValidator tokenMngValidator;

	 @Autowired
	 TokenMngService tokenMngService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createTokenMng(
				@RequestBody @Valid TokenMngDto tokenMngDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  tokenMngValidator.validate(tokenMngDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  TokenMng tokenMng = modelMapper.map(tokenMngDto, TokenMng.class);

		  // 레코드 등록
		  TokenMng newTokenMng = tokenMngService.create(tokenMng);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(TokenMngController.class).slash(newTokenMng.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  TokenMngResource tokenMngResource = new TokenMngResource(newTokenMng);
		  tokenMngResource.add(linkTo(TokenMngController.class).withRel("query-tokenMng"));
		  tokenMngResource.add(selfLinkBuilder.withRel("update-tokenMng"));
		  tokenMngResource.add(new Link("/docs/index.html#resources-tokenMng-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(tokenMngResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getTokenMngs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<TokenMng> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QTokenMng qTokenMng = QTokenMng.tokenMng;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qTokenMng.id.eq(searchForm.getId()));
		  }


		  Page<TokenMng> page = this.tokenMngRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new TokenMngResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-tokenMngs-list").withRel("profile"));
		  pagedResources.add(linkTo(TokenMngController.class).withRel("create-tokenMng"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getTokenMng(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<TokenMng> tokenMngOptional = this.tokenMngRepository.findById(id);

		  // 고객 정보 체크
		  if (tokenMngOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  TokenMng tokenMng = tokenMngOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  TokenMngResource tokenMngResource = new TokenMngResource(tokenMng);
		  tokenMngResource.add(new Link("/docs/index.html#resources-tokenMng-get").withRel("profile"));

		  return ResponseEntity.ok(tokenMngResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putTokenMng(@PathVariable Integer id,
											  @RequestBody @Valid TokenMngUpdateDto tokenMngUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.tokenMngValidator.validate(tokenMngUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<TokenMng> tokenMngOptional = this.tokenMngRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (tokenMngOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  TokenMng existTokenMng = tokenMngOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  TokenMng saveTokenMng = this.tokenMngService.update(tokenMngUpdateDto, existTokenMng);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  TokenMngResource tokenMngResource = new TokenMngResource(saveTokenMng);
		  tokenMngResource.add(new Link("/docs/index.html#resources-tokenMng-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(tokenMngResource);
	 }
}
