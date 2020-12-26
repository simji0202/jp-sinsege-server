package kr.co.paywith.pw.data.repository.od.exceptMst;

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
@RequestMapping(value = "/api/exceptMst")
@Api(value = "ExceptMstController", description = "쿠폰 API", basePath = "/api/exceptMst")
public class ExceptMstController extends CommonController {

	 @Autowired
	 ExceptMstRepository exceptMstRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 ExceptMstValidator exceptMstValidator;

	 @Autowired
	 ExceptMstService exceptMstService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createExceptMst(
				@RequestBody @Valid ExceptMstDto exceptMstDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  exceptMstValidator.validate(exceptMstDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  ExceptMst exceptMst = modelMapper.map(exceptMstDto, ExceptMst.class);

		  // 레코드 등록
		  ExceptMst newExceptMst = exceptMstService.create(exceptMst);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(ExceptMstController.class).slash(newExceptMst.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ExceptMstResource exceptMstResource = new ExceptMstResource(newExceptMst);
		  exceptMstResource.add(linkTo(ExceptMstController.class).withRel("query-exceptMst"));
		  exceptMstResource.add(selfLinkBuilder.withRel("update-exceptMst"));
		  exceptMstResource.add(new Link("/docs/index.html#resources-exceptMst-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(exceptMstResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getExceptMsts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<ExceptMst> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QExceptMst qExceptMst = QExceptMst.exceptMst;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qExceptMst.id.eq(searchForm.getId()));
		  }


		  Page<ExceptMst> page = this.exceptMstRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new ExceptMstResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-exceptMsts-list").withRel("profile"));
		  pagedResources.add(linkTo(ExceptMstController.class).withRel("create-exceptMst"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getExceptMst(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<ExceptMst> exceptMstOptional = this.exceptMstRepository.findById(id);

		  // 고객 정보 체크
		  if (exceptMstOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  ExceptMst exceptMst = exceptMstOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ExceptMstResource exceptMstResource = new ExceptMstResource(exceptMst);
		  exceptMstResource.add(new Link("/docs/index.html#resources-exceptMst-get").withRel("profile"));

		  return ResponseEntity.ok(exceptMstResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putExceptMst(@PathVariable Integer id,
											  @RequestBody @Valid ExceptMstUpdateDto exceptMstUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.exceptMstValidator.validate(exceptMstUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<ExceptMst> exceptMstOptional = this.exceptMstRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (exceptMstOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  ExceptMst existExceptMst = exceptMstOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  ExceptMst saveExceptMst = this.exceptMstService.update(exceptMstUpdateDto, existExceptMst);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ExceptMstResource exceptMstResource = new ExceptMstResource(saveExceptMst);
		  exceptMstResource.add(new Link("/docs/index.html#resources-exceptMst-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(exceptMstResource);
	 }
}
