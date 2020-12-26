package kr.co.paywith.pw.data.repository.od.cate;

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
@RequestMapping(value = "/api/cate")
@Api(value = "CateController", description = "쿠폰 API", basePath = "/api/cate")
public class CateController extends CommonController {

	 @Autowired
	 CateRepository cateRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 CateValidator cateValidator;

	 @Autowired
	 CateService cateService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createCate(
				@RequestBody @Valid CateDto cateDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  cateValidator.validate(cateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Cate cate = modelMapper.map(cateDto, Cate.class);

		  // 레코드 등록
		  Cate newCate = cateService.create(cate);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(CateController.class).slash(newCate.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CateResource cateResource = new CateResource(newCate);
		  cateResource.add(linkTo(CateController.class).withRel("query-cate"));
		  cateResource.add(selfLinkBuilder.withRel("update-cate"));
		  cateResource.add(new Link("/docs/index.html#resources-cate-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(cateResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getCates(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Cate> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QCate qCate = QCate.cate;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qCate.id.eq(searchForm.getId()));
		  }


		  Page<Cate> page = this.cateRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new CateResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-cates-list").withRel("profile"));
		  pagedResources.add(linkTo(CateController.class).withRel("create-cate"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getCate(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Cate> cateOptional = this.cateRepository.findById(id);

		  // 고객 정보 체크
		  if (cateOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Cate cate = cateOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CateResource cateResource = new CateResource(cate);
		  cateResource.add(new Link("/docs/index.html#resources-cate-get").withRel("profile"));

		  return ResponseEntity.ok(cateResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putCate(@PathVariable Integer id,
											  @RequestBody @Valid CateUpdateDto cateUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.cateValidator.validate(cateUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Cate> cateOptional = this.cateRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (cateOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Cate existCate = cateOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Cate saveCate = this.cateService.update(cateUpdateDto, existCate);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  CateResource cateResource = new CateResource(saveCate);
		  cateResource.add(new Link("/docs/index.html#resources-cate-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(cateResource);
	 }
}
