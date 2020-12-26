package kr.co.paywith.pw.data.repository.od.statProc;

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
@RequestMapping(value = "/api/statProc")
@Api(value = "StatProcController", description = "쿠폰 API", basePath = "/api/statProc")
public class StatProcController extends CommonController {

	 @Autowired
	 StatProcRepository statProcRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 StatProcValidator statProcValidator;

	 @Autowired
	 StatProcService statProcService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createStatProc(
				@RequestBody @Valid StatProcDto statProcDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  statProcValidator.validate(statProcDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  StatProc statProc = modelMapper.map(statProcDto, StatProc.class);

		  // 레코드 등록
		  StatProc newStatProc = statProcService.create(statProc);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(StatProcController.class).slash(newStatProc.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StatProcResource statProcResource = new StatProcResource(newStatProc);
		  statProcResource.add(linkTo(StatProcController.class).withRel("query-statProc"));
		  statProcResource.add(selfLinkBuilder.withRel("update-statProc"));
		  statProcResource.add(new Link("/docs/index.html#resources-statProc-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(statProcResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getStatProcs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<StatProc> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QStatProc qStatProc = QStatProc.statProc;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qStatProc.id.eq(searchForm.getId()));
		  }


		  Page<StatProc> page = this.statProcRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new StatProcResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-statProcs-list").withRel("profile"));
		  pagedResources.add(linkTo(StatProcController.class).withRel("create-statProc"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getStatProc(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<StatProc> statProcOptional = this.statProcRepository.findById(id);

		  // 고객 정보 체크
		  if (statProcOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  StatProc statProc = statProcOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StatProcResource statProcResource = new StatProcResource(statProc);
		  statProcResource.add(new Link("/docs/index.html#resources-statProc-get").withRel("profile"));

		  return ResponseEntity.ok(statProcResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putStatProc(@PathVariable Integer id,
											  @RequestBody @Valid StatProcUpdateDto statProcUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.statProcValidator.validate(statProcUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<StatProc> statProcOptional = this.statProcRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (statProcOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  StatProc existStatProc = statProcOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  StatProc saveStatProc = this.statProcService.update(statProcUpdateDto, existStatProc);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  StatProcResource statProcResource = new StatProcResource(saveStatProc);
		  statProcResource.add(new Link("/docs/index.html#resources-statProc-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(statProcResource);
	 }
}
