package kr.co.paywith.pw.data.repository.mbs.goodsGrpgrp;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.goodsGrp.*;
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
@RequestMapping(value = "/api/goodsGrp")
@Api(value = "GoodsGrpController", description = "상품 API", basePath = "/api/goodsGrp")
public class GoodsGrpController extends CommonController {

    @Autowired
    GoodsGrpRepository goodsGrpRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GoodsGrpValidator goodsGrpValidator;

    @Autowired
    GoodsGrpService goodsGrpService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createGoodsGrp(
            @RequestBody @Valid GoodsGrpDto goodsGrpDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        goodsGrpValidator.validate(goodsGrpDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        GoodsGrp goodsGrp = modelMapper.map(goodsGrpDto, GoodsGrp.class);

        // 레코드 등록
        GoodsGrp newGoodsGrp = goodsGrpService.create(goodsGrp);

        ControllerLinkBuilder selfLinkBuilder = linkTo(GoodsGrpController.class).slash(newGoodsGrp.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GoodsGrpResource goodsGrpResource = new GoodsGrpResource(newGoodsGrp);
        goodsGrpResource.add(linkTo(GoodsGrpController.class).withRel("query-goodsGrp"));
        goodsGrpResource.add(selfLinkBuilder.withRel("update-goodsGrp"));
        goodsGrpResource.add(new Link("/docs/index.html#resources-goodsGrp-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(goodsGrpResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getGoodsGrps(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<GoodsGrp> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        GoodsGrpr princpal = (GoodsGrpr) authentication.getPrincipal();

        QGoodsGrp qGoodsGrp = QGoodsGrp.goodsGrp;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qGoodsGrp.id.eq(searchForm.getId()));
        }


        Page<GoodsGrp> page = this.goodsGrpRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new GoodsGrpResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-goodsGrps-list").withRel("profile"));
        pagedResources.add(linkTo(GoodsGrpController.class).withRel("create-goodsGrp"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getGoodsGrp(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<GoodsGrp> goodsGrpOptional = this.goodsGrpRepository.findById(id);

        // 고객 정보 체크
        if (goodsGrpOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        GoodsGrp goodsGrp = goodsGrpOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GoodsGrpResource goodsGrpResource = new GoodsGrpResource(goodsGrp);
        goodsGrpResource.add(new Link("/docs/index.html#resources-goodsGrp-get").withRel("profile"));

        return ResponseEntity.ok(goodsGrpResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putGoodsGrp(@PathVariable Integer id,
                                   @RequestBody @Valid GoodsGrpUpdateDto goodsGrpUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.goodsGrpValidator.validate(goodsGrpUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<GoodsGrp> goodsGrpOptional = this.goodsGrpRepository.findById(id);

        // 기존 정보 유무 체크
        if (goodsGrpOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        GoodsGrp existGoodsGrp = goodsGrpOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        GoodsGrp saveGoodsGrp = this.goodsGrpService.update(goodsGrpUpdateDto, existGoodsGrp);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GoodsGrpResource goodsGrpResource = new GoodsGrpResource(saveGoodsGrp);
        goodsGrpResource.add(new Link("/docs/index.html#resources-goodsGrp-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(goodsGrpResource);
    }


}



