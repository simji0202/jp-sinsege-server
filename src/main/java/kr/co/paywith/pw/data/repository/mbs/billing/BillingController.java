package kr.co.paywith.pw.data.repository.mbs.billing;

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
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/billing")
@Api(value = "BillingController", description = "정기 결제 API", basePath = "/api/billing")
public class BillingController extends CommonController {

    @Autowired
    BillingRepository billingRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BillingValidator billingValidator;

    @Autowired
    BillingService billingService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createBilling(
            @RequestBody @Valid BillingDto billingDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        billingValidator.validate(billingDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        Billing billing = modelMapper.map(billingDto, Billing.class);

        // 레코드 등록
        Billing newBilling = billingService.create(billing);

        ControllerLinkBuilder selfLinkBuilder = linkTo(BillingController.class).slash(newBilling.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BillingResource billingResource = new BillingResource(newBilling);
        billingResource.add(linkTo(BillingController.class).withRel("query-billing"));
        billingResource.add(selfLinkBuilder.withRel("update-billing"));
        billingResource.add(new Link("/docs/index.html#resources-billing-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(billingResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getBillings(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Billing> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Billingr princpal = (Billingr) authentication.getPrincipal();

        QBilling qBilling = QBilling.billing;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qBilling.id.eq(searchForm.getId()));
        }


        Page<Billing> page = this.billingRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new BillingResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-billings-list").withRel("profile"));
        pagedResources.add(linkTo(BillingController.class).withRel("create-billing"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getBilling(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<Billing> billingOptional = this.billingRepository.findById(id);

        // 고객 정보 체크
        if (billingOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Billing billing = billingOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BillingResource billingResource = new BillingResource(billing);
        billingResource.add(new Link("/docs/index.html#resources-billing-get").withRel("profile"));

        return ResponseEntity.ok(billingResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putBilling(@PathVariable Integer id,
                                   @RequestBody @Valid BillingUpdateDto billingUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.billingValidator.validate(billingUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<Billing> billingOptional = this.billingRepository.findById(id);

        // 기존 정보 유무 체크
        if (billingOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        Billing existBilling = billingOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Billing saveBilling = this.billingService.update(billingUpdateDto, existBilling);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BillingResource billingResource = new BillingResource(saveBilling);
        billingResource.add(new Link("/docs/index.html#resources-billing-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(billingResource);
    }


}



