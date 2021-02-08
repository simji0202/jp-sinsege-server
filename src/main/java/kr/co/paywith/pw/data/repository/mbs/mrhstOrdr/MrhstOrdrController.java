package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import io.swagger.annotations.Api;
import java.util.Optional;
import javax.validation.Valid;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mrhstOrdr")
@Api(value = "MrhstOrdrController", description = "쿠폰 API", basePath = "/api/mrhstOrdr")
public class MrhstOrdrController extends CommonController {

  @Autowired
  MrhstOrdrRepository mrhstOrdrRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  MrhstOrdrValidator mrhstOrdrValidator;

  @Autowired
  MrhstOrdrService mrhstOrdrService;
//
//	 /**
//	  * 정보 등록
//	  */
//	 @PostMapping
//	 public ResponseEntity createMrhstOrdr(
//				@RequestBody @Valid MrhstOrdrDto mrhstOrdrDto,
//				Errors errors,
//				@CurrentUser Account currentUser) {
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값 체크
//		  mrhstOrdrValidator.validate(mrhstOrdrDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값을 브랜드 객채에 대입
//		  MrhstOrdr mrhstOrdr = modelMapper.map(mrhstOrdrDto, MrhstOrdr.class);
//
//		  // 레코드 등록
//		  MrhstOrdr newMrhstOrdr = mrhstOrdrService.create(mrhstOrdr);
//
//		  ControllerLinkBuilder selfLinkBuilder = linkTo(MrhstOrdrController.class).slash(newMrhstOrdr.getId());
//
//		  URI createdUri = selfLinkBuilder.toUri();
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  MrhstOrdrResource mrhstOrdrResource = new MrhstOrdrResource(newMrhstOrdr);
//		  mrhstOrdrResource.add(linkTo(MrhstOrdrController.class).withRel("query-mrhstOrdr"));
//		  mrhstOrdrResource.add(selfLinkBuilder.withRel("update-mrhstOrdr"));
//		  mrhstOrdrResource.add(new Link("/docs/index.html#resources-mrhstOrdr-create").withRel("profile"));
//
//		  return ResponseEntity.created(createdUri).body(mrhstOrdrResource);
//	 }

//	 /**
//	  * 정보취득 (조건별 page )
//	  */
//	 @GetMapping
//	 public ResponseEntity getMrhstOrdrs(SearchForm searchForm,
//												Pageable pageable,
//												PagedResourcesAssembler<MrhstOrdr> assembler
//				, @CurrentUser Account currentUser) {
//
//		  // 인증상태의 유저 정보 확인
////		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////		  User princpal = (User) authentication.getPrincipal();
//
//		  QMrhstOrdr qMrhstOrdr = QMrhstOrdr.mrhstOrdr;
//
//		  //
//		  BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//		  // 검색조건 아이디(키)
//		  if (searchForm.getId() != null) {
//				booleanBuilder.and(qMrhstOrdr.id.eq(searchForm.getId()));
//		  }
//
//
//		  Page<MrhstOrdr> page = this.mrhstOrdrRepository.findAll(booleanBuilder, pageable);
//		  var pagedResources = assembler.toResource(page, e -> new MrhstOrdrResource(e));
//		  pagedResources.add(new Link("/docs/index.html#resources-mrhstOrdrs-list").withRel("profile"));
//		  pagedResources.add(linkTo(MrhstOrdrController.class).withRel("create-mrhstOrdr"));
//		  return ResponseEntity.ok(pagedResources);
//	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getMrhstOrdr(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<MrhstOrdr> mrhstOrdrOptional = this.mrhstOrdrRepository.findById(id);

    // 고객 정보 체크
    if (mrhstOrdrOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    MrhstOrdr mrhstOrdr = mrhstOrdrOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MrhstOrdrResource mrhstOrdrResource = new MrhstOrdrResource(mrhstOrdr);
    mrhstOrdrResource.add(new Link("/docs/index.html#resources-mrhstOrdr-get").withRel("profile"));

    return ResponseEntity.ok(mrhstOrdrResource);
  }


  /**
   * 정보 변경
   */
  @PutMapping("/{id}")
  public ResponseEntity putMrhstOrdr(@PathVariable Integer id,
      @RequestBody @Valid MrhstOrdrUpdateDto mrhstOrdrUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.mrhstOrdrValidator.validate(mrhstOrdrUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 기존 테이블에서 관련 정보 취득
    Optional<MrhstOrdr> mrhstOrdrOptional = this.mrhstOrdrRepository.findById(id);

    // 기존 정보 유무 체크
    if (mrhstOrdrOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 기존 정보 취득
    MrhstOrdr existMrhstOrdr = mrhstOrdrOptional.get();

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    MrhstOrdr saveMrhstOrdr = this.mrhstOrdrService.update(mrhstOrdrUpdateDto, existMrhstOrdr);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MrhstOrdrResource mrhstOrdrResource = new MrhstOrdrResource(saveMrhstOrdr);
    mrhstOrdrResource
        .add(new Link("/docs/index.html#resources-mrhstOrdr-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(mrhstOrdrResource);
  }
}
