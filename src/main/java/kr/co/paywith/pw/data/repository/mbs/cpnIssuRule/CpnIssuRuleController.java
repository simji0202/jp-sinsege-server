package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;

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
@RequestMapping(value = "/api/cpnIssuRule")
@Api(value = "CpnIssuRuleController", description = "쿠폰 규칙 API", basePath = "/api/cpnIssuRule")
public class CpnIssuRuleController extends CommonController {

    @Autowired
    CpnIssuRuleRepository cpnIssuRuleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CpnIssuRuleValidator cpnIssuRuleValidator;

    @Autowired
    CpnIssuRuleService cpnIssuRuleService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createCpnIssuRule(
            @RequestBody @Valid CpnIssuRuleDto cpnIssuRuleDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        cpnIssuRuleValidator.validate(cpnIssuRuleDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        CpnIssuRule cpnIssuRule = modelMapper.map(cpnIssuRuleDto, CpnIssuRule.class);

        // 레코드 등록
        CpnIssuRule newCpnIssuRule = cpnIssuRuleService.create(cpnIssuRule);

        ControllerLinkBuilder selfLinkBuilder = linkTo(CpnIssuRuleController.class).slash(
            newCpnIssuRule.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnIssuRuleResource cpnIssuRuleResource = new CpnIssuRuleResource(newCpnIssuRule);
        cpnIssuRuleResource.add(linkTo(CpnIssuRuleController.class).withRel("query-cpnIssuRule"));
        cpnIssuRuleResource.add(selfLinkBuilder.withRel("update-cpnIssuRule"));
        cpnIssuRuleResource.add(new Link("/docs/index.html#resources-cpnIssuRule-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(cpnIssuRuleResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getCpnIssuRules(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<CpnIssuRule> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QCpnIssuRule qCpnIssuRule = QCpnIssuRule.cpnIssuRule;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qCpnIssuRule.id.eq(searchForm.getId()));
        }


        Page<CpnIssuRule> page = this.cpnIssuRuleRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new CpnIssuRuleResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-cpnIssuRules-list").withRel("profile"));
        pagedResources.add(linkTo(CpnIssuRuleController.class).withRel("create-cpnIssuRule"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getCpnIssuRule(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<CpnIssuRule> cpnIssuRuleOptional = this.cpnIssuRuleRepository.findById(id);

        // 고객 정보 체크
        if (cpnIssuRuleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CpnIssuRule cpnIssuRule = cpnIssuRuleOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnIssuRuleResource cpnIssuRuleResource = new CpnIssuRuleResource(cpnIssuRule);
        cpnIssuRuleResource.add(new Link("/docs/index.html#resources-cpnIssuRule-get").withRel("profile"));

        return ResponseEntity.ok(cpnIssuRuleResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putCpnIssuRule(@PathVariable Integer id,
                                   @RequestBody @Valid CpnIssuRuleUpdateDto cpnIssuRuleUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.cpnIssuRuleValidator.validate(cpnIssuRuleUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<CpnIssuRule> cpnIssuRuleOptional = this.cpnIssuRuleRepository.findById(id);

        // 기존 정보 유무 체크
        if (cpnIssuRuleOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        CpnIssuRule existCpnIssuRule = cpnIssuRuleOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        CpnIssuRule saveCpnIssuRule = this.cpnIssuRuleService
            .update(cpnIssuRuleUpdateDto, existCpnIssuRule);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnIssuRuleResource cpnIssuRuleResource = new CpnIssuRuleResource(saveCpnIssuRule);
        cpnIssuRuleResource.add(new Link("/docs/index.html#resources-cpnIssuRule-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(cpnIssuRuleResource);
    }





}



