package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import io.swagger.annotations.Api;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/delngDeliv")
@Api(value = "DelngDelivController", description = "쿠폰 API", basePath = "/api/delngDeliv")
public class DelngDelivController extends CommonController {

  @Autowired
  DelngDelivRepository delngDelivRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  DelngDelivValidator delngDelivValidator;

  @Autowired
  DelngDelivService delngDelivService;

//  /**
//   * 정보 등록
//   */
//  @PostMapping
//  public ResponseEntity createDelngDeliv(
//      @RequestBody @Valid DelngDelivDto delngDelivDto,
//      Errors errors,
//      @CurrentUser Account currentUser) {
//    if (errors.hasErrors()) {
//      return badRequest(errors);
//    }
//
//    // 입력값 체크
//    delngDelivValidator.validate(delngDelivDto, errors);
//    if (errors.hasErrors()) {
//      return badRequest(errors);
//    }
//
//    // 입력값을 브랜드 객채에 대입
//    DelngDeliv delngDeliv = modelMapper.map(delngDelivDto, DelngDeliv.class);
//
//    // 레코드 등록
//    DelngDeliv newDelngDeliv = delngDelivService.create(delngDeliv);
//
//    ControllerLinkBuilder selfLinkBuilder = linkTo(DelngDelivController.class)
//        .slash(newDelngDeliv.getId());
//
//    URI createdUri = selfLinkBuilder.toUri();
//    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//    DelngDelivResource delngDelivResource = new DelngDelivResource(newDelngDeliv);
//    delngDelivResource.add(linkTo(DelngDelivController.class).withRel("query-delngDeliv"));
//    delngDelivResource.add(selfLinkBuilder.withRel("update-delngDeliv"));
//    delngDelivResource
//        .add(new Link("/docs/index.html#resources-delngDeliv-create").withRel("profile"));
//
//    return ResponseEntity.created(createdUri).body(delngDelivResource);
//  }
//
//
//  /**
//   * 정보취득 (조건별 page )
//   */
//  @GetMapping
//  public ResponseEntity getDelngDelivs(SearchForm searchForm,
//      Pageable pageable,
//      PagedResourcesAssembler<DelngDeliv> assembler
//      , @CurrentUser Account currentUser) {
//
//    // 인증상태의 유저 정보 확인
////		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////		  User princpal = (User) authentication.getPrincipal();
//
//    QDelngDeliv qDelngDeliv = QDelngDeliv.delngDeliv;
//
//    //
//    BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//    // 검색조건 아이디(키)
//    if (searchForm.getId() != null) {
//      booleanBuilder.and(qDelngDeliv.id.eq(searchForm.getId()));
//    }
//
//    Page<DelngDeliv> page = this.delngDelivRepository.findAll(booleanBuilder, pageable);
//    var pagedResources = assembler.toResource(page, e -> new DelngDelivResource(e));
//    pagedResources.add(new Link("/docs/index.html#resources-delngDelivs-list").withRel("profile"));
//    pagedResources.add(linkTo(DelngDelivController.class).withRel("create-delngDeliv"));
//    return ResponseEntity.ok(pagedResources);
//  }
//
//  private ResponseEntity badRequest(Errors errors) {
//    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
//  }
//
//
//  /**
//   * 정보취득 (1건 )
//   */
//  @GetMapping("/{id}")
//  public ResponseEntity getDelngDeliv(@PathVariable Integer id,
//      @CurrentUser Account currentUser) {
//
//    Optional<DelngDeliv> delngDelivOptional = this.delngDelivRepository.findById(id);
//
//    // 고객 정보 체크
//    if (delngDelivOptional.isEmpty()) {
//      return ResponseEntity.notFound().build();
//    }
//
//    DelngDeliv delngDeliv = delngDelivOptional.get();
//
//    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//    DelngDelivResource delngDelivResource = new DelngDelivResource(delngDeliv);
//    delngDelivResource
//        .add(new Link("/docs/index.html#resources-delngDeliv-get").withRel("profile"));
//
//    return ResponseEntity.ok(delngDelivResource);
//  }
//
//
//  /**
//   * 정보 변경
//   */
//  @PutMapping("/{id}")
//  public ResponseEntity putDelngDeliv(@PathVariable Integer id,
//      @RequestBody @Valid DelngDelivUpdateDto delngDelivUpdateDto,
//      Errors errors,
//      @CurrentUser Account currentUser) {
//    // 입력체크
//    if (errors.hasErrors()) {
//      return badRequest(errors);
//    }
//
//    // 논리적 오류 (제약조건) 체크
//    this.delngDelivValidator.validate(delngDelivUpdateDto, errors);
//    if (errors.hasErrors()) {
//      return badRequest(errors);
//    }
//
//    // 기존 테이블에서 관련 정보 취득
//    Optional<DelngDeliv> delngDelivOptional = this.delngDelivRepository.findById(id);
//
//    // 기존 정보 유무 체크
//    if (delngDelivOptional.isEmpty()) {
//      // 404 Error return
//      return ResponseEntity.notFound().build();
//    }
//
//    // 기존 정보 취득
//    DelngDeliv existDelngDeliv = delngDelivOptional.get();
//
//    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//    DelngDeliv saveDelngDeliv = this.delngDelivService.update(delngDelivUpdateDto, existDelngDeliv);
//
//    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//    DelngDelivResource delngDelivResource = new DelngDelivResource(saveDelngDeliv);
//    delngDelivResource
//        .add(new Link("/docs/index.html#resources-delngDeliv-update").withRel("profile"));
//
//    // 정상적 처리
//    return ResponseEntity.ok(delngDelivResource);
//  }
}
