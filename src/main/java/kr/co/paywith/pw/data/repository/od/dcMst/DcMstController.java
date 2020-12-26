package kr.co.paywith.pw.data.repository.od.dcMst;

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
@RequestMapping(value = "/api/dcMst")
@Api(value = "DcMstController", description = "쿠폰 API", basePath = "/api/dcMst")
public class DcMstController extends CommonController {

	 @Autowired
	 DcMstRepository dcMstRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 DcMstValidator dcMstValidator;

	 @Autowired
	 DcMstService dcMstService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createDcMst(
				@RequestBody @Valid DcMstDto dcMstDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  dcMstValidator.validate(dcMstDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  DcMst dcMst = modelMapper.map(dcMstDto, DcMst.class);

		  // 레코드 등록
		  DcMst newDcMst = dcMstService.create(dcMst);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(DcMstController.class).slash(newDcMst.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DcMstResource dcMstResource = new DcMstResource(newDcMst);
		  dcMstResource.add(linkTo(DcMstController.class).withRel("query-dcMst"));
		  dcMstResource.add(selfLinkBuilder.withRel("update-dcMst"));
		  dcMstResource.add(new Link("/docs/index.html#resources-dcMst-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(dcMstResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getDcMsts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<DcMst> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QDcMst qDcMst = QDcMst.dcMst;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qDcMst.id.eq(searchForm.getId()));
		  }


		  Page<DcMst> page = this.dcMstRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new DcMstResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-dcMsts-list").withRel("profile"));
		  pagedResources.add(linkTo(DcMstController.class).withRel("create-dcMst"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getDcMst(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<DcMst> dcMstOptional = this.dcMstRepository.findById(id);

		  // 고객 정보 체크
		  if (dcMstOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  DcMst dcMst = dcMstOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DcMstResource dcMstResource = new DcMstResource(dcMst);
		  dcMstResource.add(new Link("/docs/index.html#resources-dcMst-get").withRel("profile"));

		  return ResponseEntity.ok(dcMstResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putDcMst(@PathVariable Integer id,
											  @RequestBody @Valid DcMstUpdateDto dcMstUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.dcMstValidator.validate(dcMstUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<DcMst> dcMstOptional = this.dcMstRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (dcMstOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  DcMst existDcMst = dcMstOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  DcMst saveDcMst = this.dcMstService.update(dcMstUpdateDto, existDcMst);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  DcMstResource dcMstResource = new DcMstResource(saveDcMst);
		  dcMstResource.add(new Link("/docs/index.html#resources-dcMst-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(dcMstResource);
	 }
}
