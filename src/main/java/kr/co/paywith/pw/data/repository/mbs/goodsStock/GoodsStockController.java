package kr.co.paywith.pw.data.repository.mbs.goodsStock;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용 처리
 */
@RestController
@RequestMapping(value = "/api/goodsStock")
@Api(value = "GoodsStockController", description = "상품 API", basePath = "/api/goodsStock")
public class GoodsStockController extends CommonController {

  @Autowired
  GoodsStockRepository goodsStockRepository;

  @Autowired
  ModelMapper modelMapper;

//    @Autowired
//    GoodsStockValidator goodsStockValidator;
//
//    @Autowired
//    GoodsStockService goodsStockService;

//    /**
//     * 정보 등록
//     */
//    @PostMapping
//    public ResponseEntity createGoodsStock(
//            @RequestBody @Valid GoodsStockHistDto goodsStockDto,
//            Errors errors,
//            @CurrentUser Account currentUser) {
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//
//        // 입력값 체크
//        goodsStockValidator.validate(goodsStockDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//
//        // 입력값을 브랜드 객채에 대입
//        GoodsStock goodsStock = modelMapper.map(goodsStockDto, GoodsStock.class);
//
//        // 레코드 등록
//        GoodsStock newGoodsStock = goodsStockService.create(goodsStock);
//
//        ControllerLinkBuilder selfLinkBuilder = linkTo(
//            GoodsStockController.class).slash(newGoodsStock.getId());
//
//        URI createdUri = selfLinkBuilder.toUri();
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        GoodsStockResource goodsStockResource = new GoodsStockResource(newGoodsStock);
//        goodsStockResource.add(linkTo(
//            GoodsStockController.class).withRel("query-goodsStock"));
//        goodsStockResource.add(selfLinkBuilder.withRel("update-goodsStock"));
//        goodsStockResource.add(new Link("/docs/index.html#resources-goodsStock-create").withRel("profile"));
//
//        return ResponseEntity.created(createdUri).body(goodsStockResource);
//    }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getGoodsStocks(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<GoodsStock> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        GoodsStockr princpal = (GoodsStockr) authentication.getPrincipal();

    QGoodsStock qGoodsStock = QGoodsStock.goodsStock;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qGoodsStock.id.eq(searchForm.getId()));
    }

    booleanBuilder.and(
        searchForm.getGoodsId() != null ? qGoodsStock.goods.id.eq(searchForm.getGoodsId()) : null);
    booleanBuilder.and(
        searchForm.getMrhstId() != null ? qGoodsStock.mrhst.id.eq(searchForm.getMrhstId()) : null);

    Page<GoodsStock> page = this.goodsStockRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new GoodsStockResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-goodsStocks-list").withRel("profile"));
    pagedResources.add(linkTo(
        GoodsStockController.class).withRel("create-goodsStock"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

//    /**
//     * 정보취득 (1건 )
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity getGoodsStock(@PathVariable Integer id,
//                                   @CurrentUser Account currentUser) {
//
//        Optional<GoodsStock> goodsStockOptional = this.goodsStockRepository.findById(id);
//
//        // 고객 정보 체크
//        if (goodsStockOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        GoodsStock goodsStock = goodsStockOptional.get();
//
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        GoodsStockResource goodsStockResource = new GoodsStockResource(goodsStock);
//        goodsStockResource.add(new Link("/docs/index.html#resources-goodsStock-get").withRel("profile"));
//
//        return ResponseEntity.ok(goodsStockResource);
//    }

//    /**
//     * 정보 변경
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity putGoodsStock(@PathVariable Integer id,
//                                   @RequestBody @Valid GoodsStockUpdateDto goodsStockUpdateDto,
//                                   Errors errors,
//                                   @CurrentUser Account currentUser) {
//        // 입력체크
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 논리적 오류 (제약조건) 체크
//        this.goodsStockValidator.validate(goodsStockUpdateDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 기존 테이블에서 관련 정보 취득
//        Optional<GoodsStock> goodsStockOptional = this.goodsStockRepository.findById(id);
//
//        // 기존 정보 유무 체크
//        if (goodsStockOptional.isEmpty()) {
//            // 404 Error return
//            return ResponseEntity.notFound().build();
//        }
//
//        // 기존 정보 취득
//        GoodsStock existGoodsStock = goodsStockOptional.get();
//
//
//        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//        GoodsStock saveGoodsStock = this.goodsStockService.update(goodsStockUpdateDto, existGoodsStock);
//
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        GoodsStockResource goodsStockResource = new GoodsStockResource(saveGoodsStock);
//        goodsStockResource.add(new Link("/docs/index.html#resources-goodsStock-update").withRel("profile"));
//
//        // 정상적 처리
//        return ResponseEntity.ok(goodsStockResource);
//    }

}



