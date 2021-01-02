package kr.co.paywith.pw.data.repository.mbs.stamp;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/stamp")
@Api(value = "StampController", description = "쿠폰 API", basePath = "/api/stamp")
public class StampController extends CommonController {

	 @Autowired
	 StampRepository stampRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 StampValidator stampValidator;

	 @Autowired
	 StampService stampService;

	 // stamp 등록은 issu 통해서나 다른 내부 로직 통해서 저장
//	 /**
//	  * 정보 등록
//	  */
//	 @PostMapping
//	 public ResponseEntity createStamp(
//				@RequestBody @Valid StampDto stampDto,
//				Errors errors,
//				@CurrentUser Account currentUser) {
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값 체크
//		  stampValidator.validate(stampDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값을 브랜드 객채에 대입
//		  Stamp stamp = modelMapper.map(stampDto, Stamp.class);
//
//		  // 레코드 등록
//		  Stamp newStamp = stampService.create(stamp);
//
//		  ControllerLinkBuilder selfLinkBuilder = linkTo(StampController.class).slash(newStamp.getId());
//
//		  URI createdUri = selfLinkBuilder.toUri();
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  StampResource stampResource = new StampResource(newStamp);
//		  stampResource.add(linkTo(StampController.class).withRel("query-stamp"));
//		  stampResource.add(selfLinkBuilder.withRel("update-stamp"));
//		  stampResource.add(new Link("/docs/index.html#resources-stamp-create").withRel("profile"));
//
//		  return ResponseEntity.created(createdUri).body(stampResource);
//	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getStamps(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Stamp> assembler
				, @CurrentUser Account currentUser) {

		 // kms: currentUser 와 차이는??
		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QStamp qStamp = QStamp.stamp;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qStamp.id.eq(searchForm.getId()));
		  }

		  // 검색조건 회원
			booleanBuilder.and(qStamp.stampHist.userInfo.id.eq(currentUser.getUserInfo().getId()));


		  Page<Stamp> page = this.stampRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new StampResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-stamps-list").withRel("profile"));
		  pagedResources.add(linkTo(StampController.class).withRel("create-stamp"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getStamp(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Stamp> stampOptional = this.stampRepository.findById(id);

		  // 고객 정보 체크
		  if (stampOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Stamp stamp = stampOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StampResource stampResource = new StampResource(stamp);
		  stampResource.add(new Link("/docs/index.html#resources-stamp-get").withRel("profile"));

		  return ResponseEntity.ok(stampResource);
	 }

// stamp 변경은 다른 내부 로직 통해서 저장
//	 /**
//	  * 정보 변경
//	  */
//	 @PutMapping("/{id}")
//	 public ResponseEntity putStamp(@PathVariable Integer id,
//											  @RequestBody @Valid StampUpdateDto stampUpdateDto,
//											  Errors errors,
//											  @CurrentUser Account currentUser) {
//		  // 입력체크
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 논리적 오류 (제약조건) 체크
//		  this.stampValidator.validate(stampUpdateDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 기존 테이블에서 관련 정보 취득
//		  Optional<Stamp> stampOptional = this.stampRepository.findById(id);
//
//		  // 기존 정보 유무 체크
//		  if (stampOptional.isEmpty()) {
//				// 404 Error return
//				return ResponseEntity.notFound().build();
//		  }
//
//		  // 기존 정보 취득
//		  Stamp existStamp = stampOptional.get();
//
//		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//		  Stamp saveStamp = this.stampService.update(stampUpdateDto, existStamp);
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  StampResource stampResource = new StampResource(saveStamp);
//		  stampResource.add(new Link("/docs/index.html#resources-stamp-update").withRel("profile"));
//
//		  // 정상적 처리
//		  return ResponseEntity.ok(stampResource);
//	 }
}
