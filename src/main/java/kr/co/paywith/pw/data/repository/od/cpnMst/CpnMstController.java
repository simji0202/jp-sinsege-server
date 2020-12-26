package kr.co.paywith.pw.data.repository.od.cpnMst;

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
@RequestMapping(value = "/api/cpnMst")
@Api(value = "CpnMstController", description = "쿠폰 API", basePath = "/api/cpnMst")
public class CpnMstController extends CommonController {

	 @Autowired
	 CpnMstRepository cpnMstRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 CpnMstValidator cpnMstValidator;

	 @Autowired
	 CpnMstService cpnMstService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createCpnMst(
				@RequestBody @Valid CpnMstDto cpnMstDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  cpnMstValidator.validate(cpnMstDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  CpnMst cpnMst = modelMapper.map(cpnMstDto, CpnMst.class);

		  // 레코드 등록
		  CpnMst newCpnMst = cpnMstService.create(cpnMst);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(CpnMstController.class).slash(newCpnMst.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CpnMstResource cpnMstResource = new CpnMstResource(newCpnMst);
		  cpnMstResource.add(linkTo(CpnMstController.class).withRel("query-cpnMst"));
		  cpnMstResource.add(selfLinkBuilder.withRel("update-cpnMst"));
		  cpnMstResource.add(new Link("/docs/index.html#resources-cpnMst-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(cpnMstResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getCpnMsts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<CpnMst> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QCpnMst qCpnMst = QCpnMst.cpnMst;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qCpnMst.id.eq(searchForm.getId()));
		  }


		  Page<CpnMst> page = this.cpnMstRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new CpnMstResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-cpnMsts-list").withRel("profile"));
		  pagedResources.add(linkTo(CpnMstController.class).withRel("create-cpnMst"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getCpnMst(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<CpnMst> cpnMstOptional = this.cpnMstRepository.findById(id);

		  // 고객 정보 체크
		  if (cpnMstOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  CpnMst cpnMst = cpnMstOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CpnMstResource cpnMstResource = new CpnMstResource(cpnMst);
		  cpnMstResource.add(new Link("/docs/index.html#resources-cpnMst-get").withRel("profile"));

		  return ResponseEntity.ok(cpnMstResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putCpnMst(@PathVariable Integer id,
											  @RequestBody @Valid CpnMstUpdateDto cpnMstUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.cpnMstValidator.validate(cpnMstUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<CpnMst> cpnMstOptional = this.cpnMstRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (cpnMstOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  CpnMst existCpnMst = cpnMstOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  CpnMst saveCpnMst = this.cpnMstService.update(cpnMstUpdateDto, existCpnMst);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CpnMstResource cpnMstResource = new CpnMstResource(saveCpnMst);
		  cpnMstResource.add(new Link("/docs/index.html#resources-cpnMst-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(cpnMstResource);
	 }
}
