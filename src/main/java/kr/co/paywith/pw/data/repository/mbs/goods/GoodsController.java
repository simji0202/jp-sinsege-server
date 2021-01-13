package kr.co.paywith.pw.data.repository.mbs.goods;

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

/**
 * 사용 처리
 */
@RestController
@RequestMapping(value = "/api/goods")
@Api(value = "GoodsController", description = "상품 API", basePath = "/api/goods")
public class GoodsController extends CommonController {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GoodsValidator goodsValidator;

    @Autowired
    GoodsService goodsService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createGoods(
            @RequestBody @Valid GoodsDto goodsDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        goodsValidator.validate(goodsDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값을 브랜드 객채에 대입
        Goods goods = modelMapper.map(goodsDto, Goods.class);

        // 레코드 등록
        Goods newGoods = goodsService.create(goods);

        ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsController.class).slash(newGoods.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GoodsResource goodsResource = new GoodsResource(newGoods);
        goodsResource.add(linkTo(GoodsController.class).withRel("query-goods"));
        goodsResource.add(selfLinkBuilder.withRel("update-goods"));
        goodsResource.add(new Link("/docs/index.html#resources-goods-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(goodsResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getGoodss(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Goods> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Goodsr princpal = (Goodsr) authentication.getPrincipal();

        QGoods qGoods = QGoods.goods;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qGoods.id.eq(searchForm.getId()));
        }


        Page<Goods> page = this.goodsRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new GoodsResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-goodss-list").withRel("profile"));
        pagedResources.add(linkTo(GoodsController.class).withRel("create-goods"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getGoods(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<Goods> goodsOptional = this.goodsRepository.findById(id);

        // 고객 정보 체크
        if (goodsOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Goods goods = goodsOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GoodsResource goodsResource = new GoodsResource(goods);
        goodsResource.add(new Link("/docs/index.html#resources-goods-get").withRel("profile"));

        return ResponseEntity.ok(goodsResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putGoods(@PathVariable Integer id,
                                   @RequestBody @Valid GoodsUpdateDto goodsUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.goodsValidator.validate(goodsUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<Goods> goodsOptional = this.goodsRepository.findById(id);

        // 기존 정보 유무 체크
        if (goodsOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        Goods existGoods = goodsOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Goods saveGoods = this.goodsService.update(goodsUpdateDto, existGoods);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GoodsResource goodsResource = new GoodsResource(saveGoods);
        goodsResource.add(new Link("/docs/index.html#resources-goods-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(goodsResource);
    }


}



