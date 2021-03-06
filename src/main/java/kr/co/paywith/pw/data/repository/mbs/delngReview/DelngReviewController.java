package kr.co.paywith.pw.data.repository.mbs.delngReview;

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
@RequestMapping(value = "/api/delngReview")
@Api(value = "DelngReviewController", description = "쿠폰 API", basePath = "/api/delngReview")
public class DelngReviewController extends CommonController {

  @Autowired
  DelngReviewRepository delngReviewRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  DelngReviewValidator delngReviewValidator;

  @Autowired
  DelngReviewService delngReviewService;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createDelngReview(
      @RequestBody @Valid DelngReviewDto delngReviewDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    delngReviewValidator.validate(delngReviewDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    DelngReview delngReview = modelMapper.map(delngReviewDto, DelngReview.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      delngReview.setCreateBy(currentUser.getAccountId());
      delngReview.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    DelngReview newDelngReview = delngReviewService.create(delngReview);

    ControllerLinkBuilder selfLinkBuilder = linkTo(DelngReviewController.class)
        .slash(newDelngReview.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    DelngReviewResource delngReviewResource = new DelngReviewResource(newDelngReview);
    delngReviewResource.add(linkTo(DelngReviewController.class).withRel("query-delngReview"));
    delngReviewResource.add(selfLinkBuilder.withRel("update-delngReview"));
    delngReviewResource
        .add(new Link("/docs/index.html#resources-delngReview-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(delngReviewResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getDelngReviews(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<DelngReview> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

    QDelngReview qDelngReview = QDelngReview.delngReview;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qDelngReview.id.eq(searchForm.getId()));
    }

    Page<DelngReview> page = this.delngReviewRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new DelngReviewResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-delngReviews-list").withRel("profile"));
    pagedResources.add(linkTo(DelngReviewController.class).withRel("create-delngReview"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getDelngReview(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<DelngReview> delngReviewOptional = this.delngReviewRepository.findById(id);

    // 고객 정보 체크
    if (delngReviewOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    DelngReview delngReview = delngReviewOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    DelngReviewResource delngReviewResource = new DelngReviewResource(delngReview);
    delngReviewResource
        .add(new Link("/docs/index.html#resources-delngReview-get").withRel("profile"));

    return ResponseEntity.ok(delngReviewResource);
  }


  /**
   * 정보 변경
   */
  @PutMapping("/{id}")
  public ResponseEntity putDelngReview(@PathVariable Integer id,
      @RequestBody @Valid DelngReviewUpdateDto delngReviewUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.delngReviewValidator.validate(delngReviewUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 기존 테이블에서 관련 정보 취득
    Optional<DelngReview> delngReviewOptional = this.delngReviewRepository.findById(id);

    // 기존 정보 유무 체크
    if (delngReviewOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 기존 정보 취득
    DelngReview existDelngReview = delngReviewOptional.get();

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      existDelngReview.setUpdateBy(currentUser.getAccountId());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    DelngReview saveDelngReview = this.delngReviewService
        .update(delngReviewUpdateDto, existDelngReview);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    DelngReviewResource delngReviewResource = new DelngReviewResource(saveDelngReview);
    delngReviewResource
        .add(new Link("/docs/index.html#resources-delngReview-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(delngReviewResource);
  }
}
