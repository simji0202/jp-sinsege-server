package kr.co.paywith.pw.data.repository.od.goodsOpt;

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
@RequestMapping(value = "/api/goodsOpt")
@Api(value = "GoodsOptController", description = "쿠폰 API", basePath = "/api/goodsOpt")
public class GoodsOptController extends CommonController {

	 @Autowired
	 GoodsOptRepository goodsOptRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 GoodsOptValidator goodsOptValidator;

	 @Autowired
	 GoodsOptService goodsOptService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createGoodsOpt(
				@RequestBody @Valid GoodsOptDto goodsOptDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  goodsOptValidator.validate(goodsOptDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  GoodsOpt goodsOpt = modelMapper.map(goodsOptDto, GoodsOpt.class);

		  // 레코드 등록
		  GoodsOpt newGoodsOpt = goodsOptService.create(goodsOpt);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsOptController.class).slash(newGoodsOpt.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptResource goodsOptResource = new GoodsOptResource(newGoodsOpt);
		  goodsOptResource.add(linkTo(GoodsOptController.class).withRel("query-goodsOpt"));
		  goodsOptResource.add(selfLinkBuilder.withRel("update-goodsOpt"));
		  goodsOptResource.add(new Link("/docs/index.html#resources-goodsOpt-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(goodsOptResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getGoodsOpts(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<GoodsOpt> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QGoodsOpt qGoodsOpt = QGoodsOpt.goodsOpt;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qGoodsOpt.id.eq(searchForm.getId()));
		  }


		  Page<GoodsOpt> page = this.goodsOptRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new GoodsOptResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-goodsOpts-list").withRel("profile"));
		  pagedResources.add(linkTo(GoodsOptController.class).withRel("create-goodsOpt"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getGoodsOpt(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<GoodsOpt> goodsOptOptional = this.goodsOptRepository.findById(id);

		  // 고객 정보 체크
		  if (goodsOptOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  GoodsOpt goodsOpt = goodsOptOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptResource goodsOptResource = new GoodsOptResource(goodsOpt);
		  goodsOptResource.add(new Link("/docs/index.html#resources-goodsOpt-get").withRel("profile"));

		  return ResponseEntity.ok(goodsOptResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putGoodsOpt(@PathVariable Integer id,
											  @RequestBody @Valid GoodsOptUpdateDto goodsOptUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.goodsOptValidator.validate(goodsOptUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<GoodsOpt> goodsOptOptional = this.goodsOptRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (goodsOptOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  GoodsOpt existGoodsOpt = goodsOptOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  GoodsOpt saveGoodsOpt = this.goodsOptService.update(goodsOptUpdateDto, existGoodsOpt);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  GoodsOptResource goodsOptResource = new GoodsOptResource(saveGoodsOpt);
		  goodsOptResource.add(new Link("/docs/index.html#resources-goodsOpt-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(goodsOptResource);
	 }
}
