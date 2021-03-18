package kr.co.paywith.pw.data.repository.mbs.gcct;

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
import kr.co.paywith.pw.data.repository.mbs.brand.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/gcct")
@Api(value = "GcctController", description = "상품권 API", basePath = "/api/gcct")
public class GcctController extends CommonController {

  @Autowired
  GcctRepository gcctRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  GcctValidator gcctValidator;

  @Autowired
  GcctService gcctService;

  @Autowired
  private BrandService brandService;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createGcct(
      @RequestBody @Valid GcctDto gcctDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    gcctValidator.validate(gcctDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    Gcct gcct = modelMapper.map(gcctDto, Gcct.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      gcct.setCreateBy(currentUser.getAccountId());
      gcct.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    Gcct newGcct = gcctService.create(gcct);

    ControllerLinkBuilder selfLinkBuilder = linkTo(GcctController.class).slash(newGcct.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    GcctResource gcctResource = new GcctResource(newGcct);
    gcctResource.add(linkTo(GcctController.class).withRel("query-gcct"));
    gcctResource.add(selfLinkBuilder.withRel("update-gcct"));
    gcctResource.add(new Link("/docs/index.html#resources-gcct-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(gcctResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getGccts(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<Gcct> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

    QGcct qGcct = QGcct.gcct;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qGcct.id.eq(searchForm.getId()));
    }

    Page<Gcct> page = this.gcctRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new GcctResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-gccts-list").withRel("profile"));
    pagedResources.add(linkTo(GcctController.class).withRel("create-gcct"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getGcct(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<Gcct> gcctOptional = this.gcctRepository.findById(id);

    // 고객 정보 체크
    if (gcctOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Gcct gcct = gcctOptional.get();

    if (currentUser.getUserInfo() != null &&
        gcct.getToUserInfo().getUserId().equals(currentUser.getUserInfo().getUserId())) {
      // 받은 회원이 상품권을 열람할 때 로직
      if (!gcct.getReadFl()) { // 열람 여부 확인 후 true로 변경
        gcct.setReadFl(true);
        gcctRepository.save(gcct);
      }
    }

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    GcctResource gcctResource = new GcctResource(gcct);
    gcctResource.add(new Link("/docs/index.html#resources-gcct-get").withRel("profile"));

    return ResponseEntity.ok(gcctResource);
  }

//    /**
//     * 정보 변경
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity putGcct(@PathVariable Integer id,
//                                   @RequestBody @Valid GcctUpdateDto gcctUpdateDto,
//                                   Errors errors,
//                                   @CurrentUser Account currentUser) {
//        // 입력체크
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 논리적 오류 (제약조건) 체크
//        this.gcctValidator.validate(gcctUpdateDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 기존 테이블에서 관련 정보 취득
//        Optional<Gcct> gcctOptional = this.gcctRepository.findById(id);
//
//        // 기존 정보 유무 체크
//        if (gcctOptional.isEmpty()) {
//            // 404 Error return
//            return ResponseEntity.notFound().build();
//        }
//
//        // 기존 정보 취득
//        Gcct existGcct = gcctOptional.get();
//
//        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//        Gcct saveGcct = this.gcctService.update(gcctUpdateDto, existGcct);
//
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        GcctResource gcctResource = new GcctResource(saveGcct);
//        gcctResource.add(new Link("/docs/index.html#resources-gcct-update").withRel("profile"));
//
//        // 정상적 처리
//        return ResponseEntity.ok(gcctResource);
//    }

  @DeleteMapping("/{id}")
  public ResponseEntity removeGcct(@PathVariable Integer id,
      @RequestBody GcctDeleteDto gcctDeleteDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Gcct> gcctOptional = this.gcctRepository.findById(id);

    if (gcctOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Gcct gcct = gcctOptional.get();

    // 논리적 오류 (제약조건) 체크
    this.gcctValidator.validate(currentUser, gcct, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    gcct.setCancelBy(currentUser.getAccountId());
    gcctService.delete(gcct);

    // 정상적 처리
    return ResponseEntity.ok().build();
  }
}



