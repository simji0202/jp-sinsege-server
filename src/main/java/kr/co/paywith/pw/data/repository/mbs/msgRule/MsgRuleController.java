package kr.co.paywith.pw.data.repository.mbs.msgRule;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
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
@RequestMapping(value = "/api/msgRule")
@Api(value = "MsgRuleController", description = "쿠폰 API", basePath = "/api/msgRule")
public class MsgRuleController extends CommonController {

	 @Autowired
	 MsgRuleRepository msgRuleRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 MsgRuleValidator msgRuleValidator;

	 @Autowired
	 MsgRuleService msgRuleService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createMsgRule(
				@RequestBody @Valid MsgRuleDto msgRuleDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  msgRuleValidator.validate(msgRuleDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  MsgRule msgRule = modelMapper.map(msgRuleDto, MsgRule.class);

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       msgRule.setCreateBy(currentUser.getAccountId());
       msgRule.setUpdateBy(currentUser.getAccountId());
     }

		  // 레코드 등록
		  MsgRule newMsgRule = msgRuleService.create(msgRule);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(MsgRuleController.class).slash(newMsgRule.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MsgRuleResource msgRuleResource = new MsgRuleResource(newMsgRule);
		  msgRuleResource.add(linkTo(MsgRuleController.class).withRel("query-msgRule"));
		  msgRuleResource.add(selfLinkBuilder.withRel("update-msgRule"));
		  msgRuleResource.add(new Link("/docs/index.html#resources-msgRule-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(msgRuleResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getMsgRules(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<MsgRule> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QMsgRule qMsgRule = QMsgRule.msgRule;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qMsgRule.id.eq(searchForm.getId()));
		  }


		  Page<MsgRule> page = this.msgRuleRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new MsgRuleResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-msgRules-list").withRel("profile"));
		  pagedResources.add(linkTo(MsgRuleController.class).withRel("create-msgRule"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getMsgRule(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<MsgRule> msgRuleOptional = this.msgRuleRepository.findById(id);

		  // 고객 정보 체크
		  if (msgRuleOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  MsgRule msgRule = msgRuleOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MsgRuleResource msgRuleResource = new MsgRuleResource(msgRule);
		  msgRuleResource.add(new Link("/docs/index.html#resources-msgRule-get").withRel("profile"));

		  return ResponseEntity.ok(msgRuleResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putMsgRule(@PathVariable Integer id,
											  @RequestBody @Valid MsgRuleUpdateDto msgRuleUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.msgRuleValidator.validate(msgRuleUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<MsgRule> msgRuleOptional = this.msgRuleRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (msgRuleOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  MsgRule existMsgRule = msgRuleOptional.get();

     // 현재 로그인 유저 설정
     if (currentUser != null) {
       existMsgRule.setUpdateBy(currentUser.getAccountId());
     }


     // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  MsgRule saveMsgRule = this.msgRuleService.update(msgRuleUpdateDto, existMsgRule);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  MsgRuleResource msgRuleResource = new MsgRuleResource(saveMsgRule);
		  msgRuleResource.add(new Link("/docs/index.html#resources-msgRule-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(msgRuleResource);
	 }
}
