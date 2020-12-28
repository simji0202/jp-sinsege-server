package kr.co.paywith.pw.data.repository.od.timesale;

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
@RequestMapping(value = "/api/timesale")
@Api(value = "TimesaleController", description = "쿠폰 API", basePath = "/api/timesale")
public class TimesaleController extends CommonController {

	 @Autowired
	 TimesaleRepository timesaleRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 TimesaleValidator timesaleValidator;

	 @Autowired
	 TimesaleService timesaleService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createTimesale(
				@RequestBody @Valid TimesaleDto timesaleDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  timesaleValidator.validate(timesaleDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Timesale timesale = modelMapper.map(timesaleDto, Timesale.class);

		  // 레코드 등록
		  Timesale newTimesale = timesaleService.create(timesale);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(TimesaleController.class).slash(newTimesale.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  TimesaleResource timesaleResource = new TimesaleResource(newTimesale);
		  timesaleResource.add(linkTo(TimesaleController.class).withRel("query-timesale"));
		  timesaleResource.add(selfLinkBuilder.withRel("update-timesale"));
		  timesaleResource.add(new Link("/docs/index.html#resources-timesale-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(timesaleResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getTimesales(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Timesale> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QTimesale qTimesale = QTimesale.timesale;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qTimesale.id.eq(searchForm.getId()));
		  }


		  Page<Timesale> page = this.timesaleRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new TimesaleResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-timesales-list").withRel("profile"));
		  pagedResources.add(linkTo(TimesaleController.class).withRel("create-timesale"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getTimesale(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Timesale> timesaleOptional = this.timesaleRepository.findById(id);

		  // 고객 정보 체크
		  if (timesaleOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Timesale timesale = timesaleOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  TimesaleResource timesaleResource = new TimesaleResource(timesale);
		  timesaleResource.add(new Link("/docs/index.html#resources-timesale-get").withRel("profile"));

		  return ResponseEntity.ok(timesaleResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putTimesale(@PathVariable Integer id,
											  @RequestBody @Valid TimesaleUpdateDto timesaleUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.timesaleValidator.validate(timesaleUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Timesale> timesaleOptional = this.timesaleRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (timesaleOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Timesale existTimesale = timesaleOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Timesale saveTimesale = this.timesaleService.update(timesaleUpdateDto, existTimesale);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  TimesaleResource timesaleResource = new TimesaleResource(saveTimesale);
		  timesaleResource.add(new Link("/docs/index.html#resources-timesale-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(timesaleResource);
	 }
}
