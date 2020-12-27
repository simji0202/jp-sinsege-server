package kr.co.paywith.pw.data.repository.mbs.chrgMass;

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
@RequestMapping(value = "/api/chrgMass")
@Api(value = "ChrgMassController", description = "쿠폰 API", basePath = "/api/chrgMass")
public class ChrgMassController extends CommonController {

	 @Autowired
	 ChrgMassRepository chrgMassRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 ChrgMassValidator chrgMassValidator;

	 @Autowired
	 ChrgMassService chrgMassService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createChrgMass(
				@RequestBody @Valid ChrgMassDto chrgMassDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  chrgMassValidator.validate(chrgMassDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  ChrgMass chrgMass = modelMapper.map(chrgMassDto, ChrgMass.class);

		  // 레코드 등록
		  ChrgMass newChrgMass = chrgMassService.create(chrgMass);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(ChrgMassController.class).slash(newChrgMass.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ChrgMassResource chrgMassResource = new ChrgMassResource(newChrgMass);
		  chrgMassResource.add(linkTo(ChrgMassController.class).withRel("query-chrgMass"));
		  chrgMassResource.add(selfLinkBuilder.withRel("update-chrgMass"));
		  chrgMassResource.add(new Link("/docs/index.html#resources-chrgMass-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(chrgMassResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getChrgMasss(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<ChrgMass> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QChrgMass qChrgMass = QChrgMass.chrgMass;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qChrgMass.id.eq(searchForm.getId()));
		  }


		  Page<ChrgMass> page = this.chrgMassRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new ChrgMassResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-chrgMasss-list").withRel("profile"));
		  pagedResources.add(linkTo(ChrgMassController.class).withRel("create-chrgMass"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getChrgMass(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<ChrgMass> chrgMassOptional = this.chrgMassRepository.findById(id);

		  // 고객 정보 체크
		  if (chrgMassOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  ChrgMass chrgMass = chrgMassOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ChrgMassResource chrgMassResource = new ChrgMassResource(chrgMass);
		  chrgMassResource.add(new Link("/docs/index.html#resources-chrgMass-get").withRel("profile"));

		  return ResponseEntity.ok(chrgMassResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putChrgMass(@PathVariable Integer id,
											  @RequestBody @Valid ChrgMassUpdateDto chrgMassUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.chrgMassValidator.validate(chrgMassUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<ChrgMass> chrgMassOptional = this.chrgMassRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (chrgMassOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  ChrgMass existChrgMass = chrgMassOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  ChrgMass saveChrgMass = this.chrgMassService.update(chrgMassUpdateDto, existChrgMass);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ChrgMassResource chrgMassResource = new ChrgMassResource(saveChrgMass);
		  chrgMassResource.add(new Link("/docs/index.html#resources-chrgMass-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(chrgMassResource);
	 }
}
