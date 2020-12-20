package kr.co.paywith.pw.data.repository.mbs.cpnRule;

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

@RestController
@RequestMapping(value = "/api/cpnRule")
@Api(value = "CpnRuleController", description = "쿠폰 규칙 API", basePath = "/api/cpnRule")
public class CpnRuleController extends CommonController {

    @Autowired
    CpnRuleRepository cpnRuleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CpnRuleValidator cpnRuleValidator;

    @Autowired
    CpnRuleService cpnRuleService;

    @PostMapping
    public ResponseEntity createCpnRule(
            @RequestBody @Valid CpnRuleDto cpnRuleDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        cpnRuleValidator.validate(cpnRuleDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        CpnRule cpnRule = modelMapper.map(cpnRuleDto, CpnRule.class);

        // 레코드 등록
        CpnRule newCpnRule = cpnRuleService.create(cpnRule);

        ControllerLinkBuilder selfLinkBuilder = linkTo(CpnRuleController.class).slash(newCpnRule.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnRuleResource cpnRuleResource = new CpnRuleResource(newCpnRule);
        cpnRuleResource.add(linkTo(CpnRuleController.class).withRel("query-cpnRule"));
        cpnRuleResource.add(selfLinkBuilder.withRel("update-cpnRule"));
        cpnRuleResource.add(new Link("/docs/index.html#resources-cpnRule-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(cpnRuleResource);
    }


    @GetMapping
    public ResponseEntity getCpnRules(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<CpnRule> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QCpnRule qCpnRule = QCpnRule.cpnRule;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qCpnRule.id.eq(searchForm.getId()));
        }


        Page<CpnRule> page = this.cpnRuleRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new CpnRuleResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-cpnRules-list").withRel("profile"));
        pagedResources.add(linkTo(CpnRuleController.class).withRel("create-cpnRule"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity getCpnRule(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<CpnRule> cpnRuleOptional = this.cpnRuleRepository.findById(id);

        // 고객 정보 체크
        if (cpnRuleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CpnRule cpnRule = cpnRuleOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnRuleResource cpnRuleResource = new CpnRuleResource(cpnRule);
        cpnRuleResource.add(new Link("/docs/index.html#resources-cpnRule-get").withRel("profile"));

        return ResponseEntity.ok(cpnRuleResource);
    }


    /**
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity putCpnRule(@PathVariable Integer id,
                                   @RequestBody @Valid CpnRuleUpdateDto cpnRuleUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.cpnRuleValidator.validate(cpnRuleUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<CpnRule> cpnRuleOptional = this.cpnRuleRepository.findById(id);

        // 기존 정보 유무 체크
        if (cpnRuleOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        CpnRule existCpnRule = cpnRuleOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        CpnRule saveCpnRule = this.cpnRuleService.update(cpnRuleUpdateDto, existCpnRule);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnRuleResource cpnRuleResource = new CpnRuleResource(saveCpnRule);
        cpnRuleResource.add(new Link("/docs/index.html#resources-cpnRule-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(cpnRuleResource);
    }





}



