package kr.co.paywith.pw.data.repository.mbs.questCpnGoods;

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
@RequestMapping(value = "/api/questCpnGoods")
@Api(value = "QuestCpnGoodsController", description = "쿠폰 API", basePath = "/api/questCpnGoods")
public class QuestCpnGoodsController extends CommonController {

	 @Autowired
	 QuestCpnGoodsRepository questCpnGoodsRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 QuestCpnGoodsValidator questCpnGoodsValidator;

	 @Autowired
	 QuestCpnGoodsService questCpnGoodsService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createQuestCpnGoods(
				@RequestBody @Valid QuestCpnGoodsDto questCpnGoodsDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  questCpnGoodsValidator.validate(questCpnGoodsDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  QuestCpnGoods questCpnGoods = modelMapper.map(questCpnGoodsDto, QuestCpnGoods.class);

		  // 레코드 등록
		  QuestCpnGoods newQuestCpnGoods = questCpnGoodsService.create(questCpnGoods);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(QuestCpnGoodsController.class).slash(newQuestCpnGoods.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  QuestCpnGoodsResource questCpnGoodsResource = new QuestCpnGoodsResource(newQuestCpnGoods);
		  questCpnGoodsResource.add(linkTo(QuestCpnGoodsController.class).withRel("query-questCpnGoods"));
		  questCpnGoodsResource.add(selfLinkBuilder.withRel("update-questCpnGoods"));
		  questCpnGoodsResource.add(new Link("/docs/index.html#resources-questCpnGoods-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(questCpnGoodsResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getQuestCpnGoodss(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<QuestCpnGoods> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QQuestCpnGoods qQuestCpnGoods = QQuestCpnGoods.questCpnGoods;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qQuestCpnGoods.id.eq(searchForm.getId()));
		  }


		  Page<QuestCpnGoods> page = this.questCpnGoodsRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new QuestCpnGoodsResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-questCpnGoodss-list").withRel("profile"));
		  pagedResources.add(linkTo(QuestCpnGoodsController.class).withRel("create-questCpnGoods"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getQuestCpnGoods(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<QuestCpnGoods> questCpnGoodsOptional = this.questCpnGoodsRepository.findById(id);

		  // 고객 정보 체크
		  if (questCpnGoodsOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  QuestCpnGoods questCpnGoods = questCpnGoodsOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  QuestCpnGoodsResource questCpnGoodsResource = new QuestCpnGoodsResource(questCpnGoods);
		  questCpnGoodsResource.add(new Link("/docs/index.html#resources-questCpnGoods-get").withRel("profile"));

		  return ResponseEntity.ok(questCpnGoodsResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putQuestCpnGoods(@PathVariable Integer id,
											  @RequestBody @Valid QuestCpnGoodsUpdateDto questCpnGoodsUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.questCpnGoodsValidator.validate(questCpnGoodsUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<QuestCpnGoods> questCpnGoodsOptional = this.questCpnGoodsRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (questCpnGoodsOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  QuestCpnGoods existQuestCpnGoods = questCpnGoodsOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  QuestCpnGoods saveQuestCpnGoods = this.questCpnGoodsService.update(questCpnGoodsUpdateDto, existQuestCpnGoods);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  QuestCpnGoodsResource questCpnGoodsResource = new QuestCpnGoodsResource(saveQuestCpnGoods);
		  questCpnGoodsResource.add(new Link("/docs/index.html#resources-questCpnGoods-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(questCpnGoodsResource);
	 }
}
