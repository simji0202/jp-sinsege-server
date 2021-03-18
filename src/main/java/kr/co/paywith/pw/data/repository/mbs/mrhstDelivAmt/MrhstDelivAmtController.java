package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt.QMrhstDelivAmt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mrhstDelivAmt")
@Api(value = "MrhstDelivAmtController", description = "쿠폰 API", basePath = "/api/mrhstDelivAmt")
public class MrhstDelivAmtController extends CommonController {

	 @Autowired
   MrhstDelivAmtRepository mrhstDelivAmtRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
   MrhstDelivAmtValidator mrhstDelivAmtValidator;

	 @Autowired
   MrhstDelivAmtService mrhstDelivAmtService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createMrhstDelivAmt(
				@RequestBody @Valid MrhstDelivAmtDto mrhstDelivAmtDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  mrhstDelivAmtValidator.validate(mrhstDelivAmtDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  MrhstDelivAmt mrhstDelivAmt = modelMapper.map(mrhstDelivAmtDto, MrhstDelivAmt.class);

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       mrhstDelivAmt.setCreateBy(currentUser.getAccountId());
       mrhstDelivAmt.setUpdateBy(currentUser.getAccountId());
     }

		  // 레코드 등록
		  MrhstDelivAmt newMrhstDelivAmt = mrhstDelivAmtService.create(mrhstDelivAmt);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(
          MrhstDelivAmtController.class).slash(newMrhstDelivAmt.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstDelivAmtResource mrhstDelivAmtResource = new MrhstDelivAmtResource(newMrhstDelivAmt);
		  mrhstDelivAmtResource.add(linkTo(
          MrhstDelivAmtController.class).withRel("query-mrhstDelivAmt"));
		  mrhstDelivAmtResource.add(selfLinkBuilder.withRel("update-mrhstDelivAmt"));
		  mrhstDelivAmtResource.add(new Link("/docs/index.html#resources-mrhstDelivAmt-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(mrhstDelivAmtResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getMrhstDelivAmts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<MrhstDelivAmt> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QMrhstDelivAmt qMrhstDelivAmt = QMrhstDelivAmt.mrhstDelivAmt;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qMrhstDelivAmt.id.eq(searchForm.getId()));
		  }

		  booleanBuilder.and(
		  		searchForm.getMrhstId() != null ? qMrhstDelivAmt.mrhstId.eq(searchForm.getMrhstId()) : null);

		  Page<MrhstDelivAmt> page = this.mrhstDelivAmtRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new MrhstDelivAmtResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-mrhstDelivAmts-list").withRel("profile"));
		  pagedResources.add(linkTo(MrhstDelivAmtController.class).withRel("create-mrhstDelivAmt"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getMrhstDelivAmt(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<MrhstDelivAmt> mrhstDelivAmtOptional = this.mrhstDelivAmtRepository.findById(id);

		  // 고객 정보 체크
		  if (mrhstDelivAmtOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  MrhstDelivAmt mrhstDelivAmt = mrhstDelivAmtOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstDelivAmtResource mrhstDelivAmtResource = new MrhstDelivAmtResource(mrhstDelivAmt);
		  mrhstDelivAmtResource.add(new Link("/docs/index.html#resources-mrhstDelivAmt-get").withRel("profile"));

		  return ResponseEntity.ok(mrhstDelivAmtResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putMrhstDelivAmt(@PathVariable Integer id,
											  @RequestBody @Valid MrhstDelivAmtUpdateDto mrhstDelivAmtUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.mrhstDelivAmtValidator.validate(mrhstDelivAmtUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<MrhstDelivAmt> mrhstDelivAmtOptional = this.mrhstDelivAmtRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (mrhstDelivAmtOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  MrhstDelivAmt existMrhstDelivAmt = mrhstDelivAmtOptional.get();

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       existMrhstDelivAmt.setUpdateBy(currentUser.getAccountId());
     }


     // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  MrhstDelivAmt saveMrhstDelivAmt = this.mrhstDelivAmtService.update(mrhstDelivAmtUpdateDto,
          existMrhstDelivAmt);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstDelivAmtResource mrhstDelivAmtResource = new MrhstDelivAmtResource(saveMrhstDelivAmt);
		  mrhstDelivAmtResource.add(new Link("/docs/index.html#resources-mrhstDelivAmt-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(mrhstDelivAmtResource);
	 }
}
