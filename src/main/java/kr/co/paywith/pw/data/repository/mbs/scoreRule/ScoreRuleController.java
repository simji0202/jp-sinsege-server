package kr.co.paywith.pw.data.repository.mbs.scoreRule;

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
@RequestMapping(value = "/api/scoreRule")
@Api(value = "ScoreRuleController", description = "쿠폰 API", basePath = "/api/scoreRule")
public class ScoreRuleController extends CommonController {

	 @Autowired
	 ScoreRuleRepository scoreRuleRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 ScoreRuleValidator scoreRuleValidator;

	 @Autowired
	 ScoreRuleService scoreRuleService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createScoreRule(
				@RequestBody @Valid ScoreRuleDto scoreRuleDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  scoreRuleValidator.validate(scoreRuleDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  ScoreRule scoreRule = modelMapper.map(scoreRuleDto, ScoreRule.class);

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       scoreRule.setCreateBy(currentUser.getAccountId());
       scoreRule.setUpdateBy(currentUser.getAccountId());
     }

		  // 레코드 등록
		  ScoreRule newScoreRule = scoreRuleService.create(scoreRule);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(ScoreRuleController.class).slash(newScoreRule.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ScoreRuleResource scoreRuleResource = new ScoreRuleResource(newScoreRule);
		  scoreRuleResource.add(linkTo(ScoreRuleController.class).withRel("query-scoreRule"));
		  scoreRuleResource.add(selfLinkBuilder.withRel("update-scoreRule"));
		  scoreRuleResource.add(new Link("/docs/index.html#resources-scoreRule-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(scoreRuleResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getScoreRules(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<ScoreRule> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QScoreRule qScoreRule = QScoreRule.scoreRule;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qScoreRule.id.eq(searchForm.getId()));
		  }


		  Page<ScoreRule> page = this.scoreRuleRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new ScoreRuleResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-scoreRules-list").withRel("profile"));
		  pagedResources.add(linkTo(ScoreRuleController.class).withRel("create-scoreRule"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getScoreRule(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<ScoreRule> scoreRuleOptional = this.scoreRuleRepository.findById(id);

		  // 고객 정보 체크
		  if (scoreRuleOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  ScoreRule scoreRule = scoreRuleOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ScoreRuleResource scoreRuleResource = new ScoreRuleResource(scoreRule);
		  scoreRuleResource.add(new Link("/docs/index.html#resources-scoreRule-get").withRel("profile"));

		  return ResponseEntity.ok(scoreRuleResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putScoreRule(@PathVariable Integer id,
											  @RequestBody @Valid ScoreRuleUpdateDto scoreRuleUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.scoreRuleValidator.validate(scoreRuleUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<ScoreRule> scoreRuleOptional = this.scoreRuleRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (scoreRuleOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  ScoreRule existScoreRule = scoreRuleOptional.get();

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       existScoreRule.setUpdateBy(currentUser.getAccountId());
     }

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  ScoreRule saveScoreRule = this.scoreRuleService.update(scoreRuleUpdateDto, existScoreRule);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ScoreRuleResource scoreRuleResource = new ScoreRuleResource(saveScoreRule);
		  scoreRuleResource.add(new Link("/docs/index.html#resources-scoreRule-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(scoreRuleResource);
	 }
}
