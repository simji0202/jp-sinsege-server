package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

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
@RequestMapping(value = "/api/notifMrhst")
@Api(value = "NotifMrhstController", description = "쿠폰 API", basePath = "/api/notifMrhst")
public class NotifMrhstController extends CommonController {

	 @Autowired
	 NotifMrhstRepository notifMrhstRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 NotifMrhstValidator notifMrhstValidator;

	 @Autowired
	 NotifMrhstService notifMrhstService;


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getNotifMrhsts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<NotifMrhst> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QNotifMrhst qNotifMrhst = QNotifMrhst.notifMrhst;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qNotifMrhst.id.eq(searchForm.getId()));
		  }


		  Page<NotifMrhst> page = this.notifMrhstRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new NotifMrhstResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-notifMrhsts-list").withRel("profile"));
		  pagedResources.add(linkTo(NotifMrhstController.class).withRel("create-notifMrhst"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getNotifMrhst(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<NotifMrhst> notifMrhstOptional = this.notifMrhstRepository.findById(id);

		  // 고객 정보 체크
		  if (notifMrhstOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  NotifMrhst notifMrhst = notifMrhstOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  NotifMrhstResource notifMrhstResource = new NotifMrhstResource(notifMrhst);
		  notifMrhstResource.add(new Link("/docs/index.html#resources-notifMrhst-get").withRel("profile"));

		  return ResponseEntity.ok(notifMrhstResource);
	 }


//	 /**
//	  * 정보 변경
//	  */
//	 @PutMapping("/{id}")
//	 public ResponseEntity putNotifMrhst(@PathVariable Integer id,
//											  @RequestBody @Valid NotifMrhstUpdateDto notifMrhstUpdateDto,
//											  Errors errors,
//											  @CurrentUser Account currentUser) {
//		  // 입력체크
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 논리적 오류 (제약조건) 체크
//		  this.notifMrhstValidator.validate(notifMrhstUpdateDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 기존 테이블에서 관련 정보 취득
//		  Optional<NotifMrhst> notifMrhstOptional = this.notifMrhstRepository.findById(id);
//
//		  // 기존 정보 유무 체크
//		  if (notifMrhstOptional.isEmpty()) {
//				// 404 Error return
//				return ResponseEntity.notFound().build();
//		  }
//
//		  // 기존 정보 취득
//		  NotifMrhst existNotifMrhst = notifMrhstOptional.get();
//
//		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//		  NotifMrhst saveNotifMrhst = this.notifMrhstService.update(notifMrhstUpdateDto, existNotifMrhst);
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  NotifMrhstResource notifMrhstResource = new NotifMrhstResource(saveNotifMrhst);
//		  notifMrhstResource.add(new Link("/docs/index.html#resources-notifMrhst-update").withRel("profile"));
//
//		  // 정상적 처리
//		  return ResponseEntity.ok(notifMrhstResource);
//	 }
}
