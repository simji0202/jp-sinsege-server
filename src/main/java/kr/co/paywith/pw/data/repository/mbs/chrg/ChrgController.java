package kr.co.paywith.pw.data.repository.mbs.chrg;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import org.modelmapper.ModelMapper;
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
@RequestMapping(value = "/api/chrg")
@Api(value = "ChrgController", description = "쿠폰 API", basePath = "/api/chrg")
public class ChrgController extends CommonController {

	 @Autowired
	 ChrgRepository chrgRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 ChrgValidator chrgValidator;

	 @Autowired
	 ChrgService chrgService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createChrg(
				@RequestBody @Valid ChrgDto chrgDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  chrgValidator.validate(chrgDto, currentUser, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Chrg chrg = modelMapper.map(chrgDto, Chrg.class);

		  // 레코드 등록
		  Chrg newChrg = chrgService.create(chrg);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(ChrgController.class).slash(newChrg.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ChrgResource chrgResource = new ChrgResource(newChrg);
		  chrgResource.add(linkTo(ChrgController.class).withRel("query-chrg"));
		  chrgResource.add(selfLinkBuilder.withRel("update-chrg"));
		  chrgResource.add(new Link("/docs/index.html#resources-chrg-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(chrgResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getChrgs(SearchForm searchForm,
									Pageable pageable,
									PagedResourcesAssembler<Chrg> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QChrg qChrg = QChrg.chrg;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qChrg.id.eq(searchForm.getId()));
		  }


		  Page<Chrg> page = this.chrgRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new ChrgResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-chrgs-list").withRel("profile"));
		  pagedResources.add(linkTo(ChrgController.class).withRel("create-chrg"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getChrg(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Chrg> chrgOptional = this.chrgRepository.findById(id);

		  // 고객 정보 체크
		  if (chrgOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Chrg chrg = chrgOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  ChrgResource chrgResource = new ChrgResource(chrg);
		  chrgResource.add(new Link("/docs/index.html#resources-chrg-get").withRel("profile"));

		  return ResponseEntity.ok(chrgResource);
	 }


//	 /**
//	  * 정보 변경
//	  */
//	 @PutMapping("/{id}")
//	 public ResponseEntity putChrg(@PathVariable Integer id,
//											  @RequestBody @Valid ChrgUpdateDto chrgUpdateDto,
//											  Errors errors,
//											  @CurrentUser Account currentUser) {
//		  // 입력체크
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 논리적 오류 (제약조건) 체크
//		  this.chrgValidator.validate(chrgUpdateDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 기존 테이블에서 관련 정보 취득
//		  Optional<Chrg> chrgOptional = this.chrgRepository.findById(id);
//
//		  // 기존 정보 유무 체크
//		  if (chrgOptional.isEmpty()) {
//				// 404 Error return
//				return ResponseEntity.notFound().build();
//		  }
//
//		  // 기존 정보 취득
//		  Chrg existChrg = chrgOptional.get();
//
//		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//		  Chrg saveChrg = this.chrgService.update(chrgUpdateDto, existChrg);
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  ChrgResource chrgResource = new ChrgResource(saveChrg);
//		  chrgResource.add(new Link("/docs/index.html#resources-chrg-update").withRel("profile"));
//
//		  // 정상적 처리
//		  return ResponseEntity.ok(chrgResource);
//	 }


	@DeleteMapping("/{id}")
	public ResponseEntity removeChrg(@PathVariable Integer id,
			@RequestBody(required = false) ChrgDeleteDto chrgDeleteDto,
			Errors errors,
			@CurrentUser Account currentUser) {
		// 입력체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Optional<Chrg> chrgOptional = this.chrgRepository.findById(id);

		if (chrgOptional.isEmpty()) {
			// 404 Error return
			return ResponseEntity.notFound().build();
		}

		Chrg chrg = chrgOptional.get();

		// 논리적 오류 (제약조건) 체크
		this.chrgValidator.validate(currentUser, chrgDeleteDto, chrg, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		chrg.setCancelBy(currentUser.getAccountId());
		chrgService.delete(chrg);

		// 정상적 처리
		return ResponseEntity.ok().build();
	}
}
