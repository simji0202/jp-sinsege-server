package kr.co.paywith.pw.data.repository.mbs.pointUseRule;

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
@RequestMapping(value = "/api/pointUseRule")
@Api(value = "PointUseRuleController", description = "쿠폰 API", basePath = "/api/pointUseRule")
public class PointUseRuleController extends CommonController {

	 @Autowired
	 PointUseRuleRepository pointUseRuleRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PointUseRuleValidator pointUseRuleValidator;

	 @Autowired
	 PointUseRuleService pointUseRuleService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPointUseRule(
				@RequestBody @Valid PointUseRuleDto pointUseRuleDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  pointUseRuleValidator.validate(pointUseRuleDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  PointUseRule pointUseRule = modelMapper.map(pointUseRuleDto, PointUseRule.class);

		  // 레코드 등록
		  PointUseRule newPointUseRule = pointUseRuleService.create(pointUseRule);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PointUseRuleController.class).slash(newPointUseRule.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointUseRuleResource pointUseRuleResource = new PointUseRuleResource(newPointUseRule);
		  pointUseRuleResource.add(linkTo(PointUseRuleController.class).withRel("query-pointUseRule"));
		  pointUseRuleResource.add(selfLinkBuilder.withRel("update-pointUseRule"));
		  pointUseRuleResource.add(new Link("/docs/index.html#resources-pointUseRule-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(pointUseRuleResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPointUseRules(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<PointUseRule> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPointUseRule qPointUseRule = QPointUseRule.pointUseRule;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPointUseRule.id.eq(searchForm.getId()));
		  }


		  Page<PointUseRule> page = this.pointUseRuleRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PointUseRuleResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-pointUseRules-list").withRel("profile"));
		  pagedResources.add(linkTo(PointUseRuleController.class).withRel("create-pointUseRule"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPointUseRule(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<PointUseRule> pointUseRuleOptional = this.pointUseRuleRepository.findById(id);

		  // 고객 정보 체크
		  if (pointUseRuleOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  PointUseRule pointUseRule = pointUseRuleOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointUseRuleResource pointUseRuleResource = new PointUseRuleResource(pointUseRule);
		  pointUseRuleResource.add(new Link("/docs/index.html#resources-pointUseRule-get").withRel("profile"));

		  return ResponseEntity.ok(pointUseRuleResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPointUseRule(@PathVariable Integer id,
											  @RequestBody @Valid PointUseRuleUpdateDto pointUseRuleUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.pointUseRuleValidator.validate(pointUseRuleUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<PointUseRule> pointUseRuleOptional = this.pointUseRuleRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (pointUseRuleOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  PointUseRule existPointUseRule = pointUseRuleOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  PointUseRule savePointUseRule = this.pointUseRuleService.update(pointUseRuleUpdateDto, existPointUseRule);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointUseRuleResource pointUseRuleResource = new PointUseRuleResource(savePointUseRule);
		  pointUseRuleResource.add(new Link("/docs/index.html#resources-pointUseRule-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(pointUseRuleResource);
	 }
}
