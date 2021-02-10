package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
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
@RequestMapping(value = "/api/goodsStockHist")
@Api(value = "GoodsStockHistController", description = "상품 재고 API", basePath = "/api/goodsStockHist")
public class GoodsStockHistController extends CommonController {

  @Autowired
  GoodsStockHistRepository goodsStockHistRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  GoodsStockHistValidator goodsStockHistValidator;

  @Autowired
  GoodsStockHistService goodsStockHistService;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createGoodsStockHist(
      @RequestBody @Valid GoodsStockHistDto goodsStockHistDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    goodsStockHistValidator.validate(goodsStockHistDto, currentUser, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    GoodsStockHist goodsStockHist = modelMapper.map(goodsStockHistDto, GoodsStockHist.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      goodsStockHist.setCreateBy(currentUser.getAccountId());
      goodsStockHist.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    GoodsStockHist newGoodsStockHist = goodsStockHistService.create(goodsStockHist);

    ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsStockHistController.class)
        .slash(newGoodsStockHist.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    GoodsStockHistResource goodsStockHistResource = new GoodsStockHistResource(newGoodsStockHist);
    goodsStockHistResource
        .add(linkTo(GoodsStockHistController.class).withRel("query-goodsStockHist"));
    goodsStockHistResource.add(selfLinkBuilder.withRel("update-goodsStockHist"));
    goodsStockHistResource
        .add(new Link("/docs/index.html#resources-goodsStockHist-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(goodsStockHistResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getGoodsStockHists(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<GoodsStockHist> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        GoodsStockHistr princpal = (GoodsStockHistr) authentication.getPrincipal();

    QGoodsStockHist qGoodsStockHist = QGoodsStockHist.goodsStockHist;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qGoodsStockHist.id.eq(searchForm.getId()));
    }

    booleanBuilder.and(searchForm.getGoodsId() != null ? qGoodsStockHist.goodsStock.goods.id
        .eq(searchForm.getGoodsId()) : null);
    booleanBuilder.and(searchForm.getMrhstId() != null ? qGoodsStockHist.goodsStock.mrhst.id
        .eq(searchForm.getMrhstId()) : null);

    Page<GoodsStockHist> page = this.goodsStockHistRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new GoodsStockHistResource(e));
    pagedResources
        .add(new Link("/docs/index.html#resources-goodsStockHists-list").withRel("profile"));
    pagedResources.add(linkTo(GoodsStockHistController.class).withRel("create-goodsStockHist"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getGoodsStockHist(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<GoodsStockHist> goodsStockHistOptional = this.goodsStockHistRepository.findById(id);

    // 고객 정보 체크
    if (goodsStockHistOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    GoodsStockHist goodsStockHist = goodsStockHistOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    GoodsStockHistResource goodsStockHistResource = new GoodsStockHistResource(goodsStockHist);
    goodsStockHistResource
        .add(new Link("/docs/index.html#resources-goodsStockHist-get").withRel("profile"));

    return ResponseEntity.ok(goodsStockHistResource);
  }

//    /**
//     * 정보 변경
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity putGoodsStockHist(@PathVariable Integer id,
//                                   @RequestBody @Valid GoodsStockHistUpdateDto goodsStockHistUpdateDto,
//                                   Errors errors,
//                                   @CurrentUser Account currentUser) {
//        // 입력체크
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 논리적 오류 (제약조건) 체크
//        this.goodsStockHistValidator.validate(goodsStockHistUpdateDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 기존 테이블에서 관련 정보 취득
//        Optional<GoodsStockHist> goodsStockHistOptional = this.goodsStockHistRepository.findById(id);
//
//        // 기존 정보 유무 체크
//        if (goodsStockHistOptional.isEmpty()) {
//            // 404 Error return
//            return ResponseEntity.notFound().build();
//        }
//
//        // 기존 정보 취득
//        GoodsStockHist existGoodsStockHist = goodsStockHistOptional.get();
//
//
//        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//        GoodsStockHist saveGoodsStockHist = this.goodsStockHistService.update(goodsStockHistUpdateDto, existGoodsStockHist);
//
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        GoodsStockHistResource goodsStockHistResource = new GoodsStockHistResource(saveGoodsStockHist);
//        goodsStockHistResource.add(new Link("/docs/index.html#resources-goodsStockHist-update").withRel("profile"));
//
//        // 정상적 처리
//        return ResponseEntity.ok(goodsStockHistResource);
//    }


}



