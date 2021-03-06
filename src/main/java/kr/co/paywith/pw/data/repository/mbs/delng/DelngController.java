package kr.co.paywith.pw.data.repository.mbs.delng;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.google.gson.Gson;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
import kr.co.paywith.pw.data.repository.enumeration.DelngType;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용 처리
 */
@RestController
@RequestMapping(value = "/api/delng")
@Api(value = "DelngController", description = "거래 단건 API", basePath = "/api/delng")
public class DelngController extends CommonController {

  @Autowired
  DelngRepository delngRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  DelngValidator delngValidator;

  @Autowired
  DelngService delngService;

  @Autowired
  Gson gson;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createDelng(
      @RequestBody @Valid DelngDto delngDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    delngValidator.validate(delngDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    Delng delng = modelMapper.map(delngDto, Delng.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      delng.setCreateBy(currentUser.getAccountId());
      delng.setUpdateBy(currentUser.getAccountId());
    }

    // 로그인 한 유저 정보
    if (DelngType.APP.equals(delngDto.getDelngType())) {
      // 앱 주문일 때는 currentUser.userInfo를 dto 에 설정
      delng.setUserInfo(currentUser.getUserInfo());
    }

    // 결제 상품 정보를 Json 데이터로 확보
    delng.setDelngGoodsListJson(gson.toJson(delngDto.getDelngGoodsList()));
    // 복합 결제 정보를 Json 데이터로 확보
    delng.setDelngPaymentJson(gson.toJson(delngDto.getDelngPaymentList()));

    // 레코드 등록
    Delng newDelng = delngService.create(delng, delngDto);

    ControllerLinkBuilder selfLinkBuilder = linkTo(DelngController.class).slash(newDelng.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    DelngResource delngResource = new DelngResource(newDelng);
    delngResource.add(linkTo(DelngController.class).withRel("query-delng"));
    delngResource.add(selfLinkBuilder.withRel("update-delng"));
    delngResource.add(new Link("/docs/index.html#resources-delng-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(delngResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getDelngs(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<Delng> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

    QDelng qDelng = QDelng.delng;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qDelng.id.eq(searchForm.getId()));
    }

    // 삭제 레코드 포함
    if (searchForm.getDelYn() != null) {
      booleanBuilder.and(searchForm.getDelYn().equals("Y") ?
          qDelng.cancelRegDttm.isNotNull() : qDelng.cancelRegDttm.isNull());
    }
    Page<Delng> page = this.delngRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new DelngResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-delngs-list").withRel("profile"));
    pagedResources.add(linkTo(DelngController.class).withRel("create-delng"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getDelng(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<Delng> delngOptional = this.delngRepository.findById(id);

    // 고객 정보 체크
    if (delngOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Delng delng = delngOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    DelngResource delngResource = new DelngResource(delng);
    delngResource.add(new Link("/docs/index.html#resources-delng-get").withRel("profile"));

    return ResponseEntity.ok(delngResource);
  }

//    /**
//     * 정보 변경
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity putDelng(@PathVariable Integer id,
//                                   @RequestBody @Valid DelngUpdateDto delngUpdateDto,
//                                   Errors errors,
//                                   @CurrentUser Account currentUser) {
//        // 입력체크
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 논리적 오류 (제약조건) 체크
//        this.delngValidator.validate(delngUpdateDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 기존 테이블에서 관련 정보 취득
//        Optional<Delng> delngOptional = this.delngRepository.findById(id);
//
//        // 기존 정보 유무 체크
//        if (delngOptional.isEmpty()) {
//            // 404 Error return
//            return ResponseEntity.notFound().build();
//        }
//
//        // 기존 정보 취득
//        Delng existDelng = delngOptional.get();
//
//
//        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//        Delng saveDelng = this.delngService.update(delngUpdateDto, existDelng);
//
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        DelngResource delngResource = new DelngResource(saveDelng);
//        delngResource.add(new Link("/docs/index.html#resources-delng-update").withRel("profile"));
//
//        // 정상적 처리
//        return ResponseEntity.ok(delngResource);
//    }

  @PostMapping("/{id}/accept")
  public ResponseEntity delngAccept(@PathVariable Integer id,
      @RequestBody(required = false) AcceptDelngDto acceptDelngDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Delng> delngOptional = this.delngRepository.findById(id);

    if (delngOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Delng delng = delngOptional.get();

    // 논리적 오류 (제약조건) 체크
    this.delngValidator.validateAccept(currentUser, delng, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    delngService.accept(acceptDelngDto, delng);

    // 정상적 처리
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/comp")
  public ResponseEntity delngComp(@PathVariable Integer id,
      @RequestBody(required = false) CompDelngDto compDelngDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Delng> delngOptional = this.delngRepository.findById(id);

    if (delngOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Delng delng = delngOptional.get();

    // 논리적 오류 (제약조건) 체크
    this.delngValidator.validateComp(currentUser, delng, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    delngService.comp(delng);

    // 정상적 처리
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity removeDelng(@PathVariable Integer id,
      @RequestBody(required = false) DelngDeleteDto delngDeleteDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Delng> delngOptional = this.delngRepository.findById(id);

    if (delngOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Delng delng = delngOptional.get();

    // 논리적 오류 (제약조건) 체크
    this.delngValidator.validate(currentUser, delng, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    delng.setCancelBy(currentUser.getAccountId());
    delngService.delete(delng);

    // 정상적 처리
    return ResponseEntity.ok().build();
  }
}



