package kr.co.paywith.pw.data.repository.od.mrhstoff;

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
@RequestMapping(value = "/api/mrhstoff")
@Api(value = "MrhstoffController", description = "쿠폰 API", basePath = "/api/mrhstoff")
public class MrhstoffController extends CommonController {

	 @Autowired
	 MrhstoffRepository mrhstoffRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 MrhstoffValidator mrhstoffValidator;

	 @Autowired
	 MrhstoffService mrhstoffService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createMrhstoff(
				@RequestBody @Valid MrhstoffDto mrhstoffDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  mrhstoffValidator.validate(mrhstoffDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Mrhstoff mrhstoff = modelMapper.map(mrhstoffDto, Mrhstoff.class);

		  // 레코드 등록
		  Mrhstoff newMrhstoff = mrhstoffService.create(mrhstoff);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(MrhstoffController.class).slash(newMrhstoff.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstoffResource mrhstoffResource = new MrhstoffResource(newMrhstoff);
		  mrhstoffResource.add(linkTo(MrhstoffController.class).withRel("query-mrhstoff"));
		  mrhstoffResource.add(selfLinkBuilder.withRel("update-mrhstoff"));
		  mrhstoffResource.add(new Link("/docs/index.html#resources-mrhstoff-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(mrhstoffResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getMrhstoffs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Mrhstoff> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QMrhstoff qMrhstoff = QMrhstoff.mrhstoff;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qMrhstoff.id.eq(searchForm.getId()));
		  }


		  Page<Mrhstoff> page = this.mrhstoffRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new MrhstoffResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-mrhstoffs-list").withRel("profile"));
		  pagedResources.add(linkTo(MrhstoffController.class).withRel("create-mrhstoff"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getMrhstoff(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Mrhstoff> mrhstoffOptional = this.mrhstoffRepository.findById(id);

		  // 고객 정보 체크
		  if (mrhstoffOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Mrhstoff mrhstoff = mrhstoffOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstoffResource mrhstoffResource = new MrhstoffResource(mrhstoff);
		  mrhstoffResource.add(new Link("/docs/index.html#resources-mrhstoff-get").withRel("profile"));

		  return ResponseEntity.ok(mrhstoffResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putMrhstoff(@PathVariable Integer id,
											  @RequestBody @Valid MrhstoffUpdateDto mrhstoffUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.mrhstoffValidator.validate(mrhstoffUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Mrhstoff> mrhstoffOptional = this.mrhstoffRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (mrhstoffOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Mrhstoff existMrhstoff = mrhstoffOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Mrhstoff saveMrhstoff = this.mrhstoffService.update(mrhstoffUpdateDto, existMrhstoff);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MrhstoffResource mrhstoffResource = new MrhstoffResource(saveMrhstoff);
		  mrhstoffResource.add(new Link("/docs/index.html#resources-mrhstoff-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(mrhstoffResource);
	 }
}
