package kr.co.paywith.pw.data.repository.mbs.mrhst;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mrhst")
@Api(value = "MrhstController", description = "가맹점 API", basePath = "/api/mrhst")
public class MrhstController extends CommonController {

  @Autowired
  MrhstRepository mrhstRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  MrhstValidator mrhstValidator;

  @Autowired
  MrhstService mrhstService;

  @PostMapping
  public ResponseEntity createMrhst(
      @RequestBody @Valid MrhstDto mrhstDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    mrhstValidator.validate(mrhstDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    Mrhst mrhst = modelMapper.map(mrhstDto, Mrhst.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      mrhst.setCreateBy(currentUser.getAccountId());
      mrhst.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    Mrhst newMrhst = mrhstService.create(mrhst);

    ControllerLinkBuilder selfLinkBuilder = linkTo(MrhstController.class).slash(newMrhst.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MrhstResource mrhstResource = new MrhstResource(newMrhst);
    mrhstResource.add(linkTo(MrhstController.class).withRel("query-mrhst"));
    mrhstResource.add(selfLinkBuilder.withRel("update-mrhst"));
    mrhstResource.add(new Link("/docs/index.html#resources-mrhst-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(mrhstResource);
  }


  @GetMapping
  public ResponseEntity getMrhsts(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<Mrhst> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

    QMrhst qMrhst = QMrhst.mrhst;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qMrhst.id.eq(searchForm.getId()));
    }

    Page<Mrhst> page = this.mrhstRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new MrhstResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-mrhsts-list").withRel("profile"));
    pagedResources.add(linkTo(MrhstController.class).withRel("create-mrhst"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   *
   */
  @GetMapping("/{id}")
  public ResponseEntity getMrhst(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<Mrhst> mrhstOptional = this.mrhstRepository.findById(id);

    // 고객 정보 체크
    if (mrhstOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Mrhst mrhst = mrhstOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MrhstResource mrhstResource = new MrhstResource(mrhst);
    mrhstResource.add(new Link("/docs/index.html#resources-mrhst-get").withRel("profile"));

    return ResponseEntity.ok(mrhstResource);
  }


  /**
   *
   */
  @PutMapping("/{id}")
  public ResponseEntity putMrhst(@PathVariable Integer id,
      @RequestBody @Valid MrhstUpdateDto mrhstUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.mrhstValidator.validate(mrhstUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 기존 테이블에서 관련 정보 취득
    Optional<Mrhst> mrhstOptional = this.mrhstRepository.findById(id);

    // 기존 정보 유무 체크
    if (mrhstOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 기존 정보 취득
    Mrhst existMrhst = mrhstOptional.get();

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      existMrhst.setUpdateBy(currentUser.getAccountId());
    }


    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Mrhst saveMrhst = this.mrhstService.update(mrhstUpdateDto, existMrhst);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MrhstResource mrhstResource = new MrhstResource(saveMrhst);
    mrhstResource.add(new Link("/docs/index.html#resources-mrhst-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(mrhstResource);
  }


}



