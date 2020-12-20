package kr.co.paywith.pw.data.repository.mbs.billingChrg;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.billingChrg.*;
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

@RestController
@RequestMapping(value = "/api/billingChrg")
@Api(value = "BillingChrgController", description = "쿠폰 API", basePath = "/api/billingChrg")
public class BillingChrgController extends CommonController {

    @Autowired
    BillingChrgRepository billingChrgRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BillingChrgValidator billingChrgValidator;

    @Autowired
    BillingChrgService billingChrgService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createBillingChrg(
            @RequestBody @Valid BillingChrgDto billingChrgDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        billingChrgValidator.validate(billingChrgDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        BillingChrg billingChrg = modelMapper.map(billingChrgDto, BillingChrg.class);

        // 레코드 등록
        BillingChrg newBillingChrg = billingChrgService.create(billingChrg);

        ControllerLinkBuilder selfLinkBuilder = linkTo(BillingChrgController.class).slash(newBillingChrg.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BillingChrgResource billingChrgResource = new BillingChrgResource(newBillingChrg);
        billingChrgResource.add(linkTo(BillingChrgController.class).withRel("query-billingChrg"));
        billingChrgResource.add(selfLinkBuilder.withRel("update-billingChrg"));
        billingChrgResource.add(new Link("/docs/index.html#resources-billingChrg-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(billingChrgResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getBillingChrgs(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<BillingChrg> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QBillingChrg qBillingChrg = QBillingChrg.billingChrg;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qBillingChrg.id.eq(searchForm.getId()));
        }


        Page<BillingChrg> page = this.billingChrgRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new BillingChrgResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-billingChrgs-list").withRel("profile"));
        pagedResources.add(linkTo(BillingChrgController.class).withRel("create-billingChrg"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getBillingChrg(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<BillingChrg> billingChrgOptional = this.billingChrgRepository.findById(id);

        // 고객 정보 체크
        if (billingChrgOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BillingChrg billingChrg = billingChrgOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BillingChrgResource billingChrgResource = new BillingChrgResource(billingChrg);
        billingChrgResource.add(new Link("/docs/index.html#resources-billingChrg-get").withRel("profile"));

        return ResponseEntity.ok(billingChrgResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putBillingChrg(@PathVariable Integer id,
                                   @RequestBody @Valid BillingChrgUpdateDto billingChrgUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.billingChrgValidator.validate(billingChrgUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<BillingChrg> billingChrgOptional = this.billingChrgRepository.findById(id);

        // 기존 정보 유무 체크
        if (billingChrgOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        BillingChrg existBillingChrg = billingChrgOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        BillingChrg saveBillingChrg = this.billingChrgService.update(billingChrgUpdateDto, existBillingChrg);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BillingChrgResource billingChrgResource = new BillingChrgResource(saveBillingChrg);
        billingChrgResource.add(new Link("/docs/index.html#resources-billingChrg-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(billingChrgResource);
    }
}



