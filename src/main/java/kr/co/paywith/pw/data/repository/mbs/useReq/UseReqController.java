package kr.co.paywith.pw.data.repository.mbs.useReq;

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
@RequestMapping(value = "/api/useReq")
@Api(value = "UseReqController", description = " 등급  API", basePath = "/api/useReq")
public class UseReqController extends CommonController {

    @Autowired
    UseReqRepository useReqRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UseReqValidator useReqValidator;

    @Autowired
    UseReqService useReqService;


    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createUseReq(
            @RequestBody @Valid UseReqDto useReqDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        useReqValidator.validate(useReqDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값 대입
        UseReq useReq = this.modelMapper.map(useReqDto, UseReq.class);

        // 레코드 등록
        UseReq newUseReq = useReqService.create(useReq);

        ControllerLinkBuilder selfLinkBuilder = linkTo(UseReqController.class).slash(newUseReq.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UseReqResource useReqResource = new UseReqResource(newUseReq);
        useReqResource.add(linkTo(UseReqController.class).withRel("query-useReq"));
        useReqResource.add(selfLinkBuilder.withRel("update-useReq"));
        useReqResource.add(new Link("/docs/index.html#resources-useReq-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(useReqResource);
    }


    /**
     * 페이지별 정보 취득
     */
    @GetMapping
    public ResponseEntity getUseReqs(SearchForm searchForm,
                                     Pageable pageable,
                                     PagedResourcesAssembler<UseReq> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QUseReq qUseReq = QUseReq.useReq;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qUseReq.id.eq(searchForm.getId()));
        }


        Page<UseReq> page = this.useReqRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new UseReqResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-useReqs-list").withRel("profile"));
        pagedResources.add(linkTo(UseReqController.class).withRel("create-useReq"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보 취득
     */
    @GetMapping("/{id}")
    public ResponseEntity getUseReq(@PathVariable Integer id,
                                    @CurrentUser Account currentUser) {

        Optional<UseReq> useReqOptional = this.useReqRepository.findById(id);

        // 고객 정보 체크
        if (useReqOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UseReq useReq = useReqOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UseReqResource useReqResource = new UseReqResource(useReq);
        useReqResource.add(new Link("/docs/index.html#resources-useReq-get").withRel("profile"));

        return ResponseEntity.ok(useReqResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putUseReq(@PathVariable Integer id,
                                    @RequestBody @Valid UseReqUpdateDto useReqUpdateDto,
                                    Errors errors,
                                    @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.useReqValidator.validate(useReqUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<UseReq> useReqOptional = this.useReqRepository.findById(id);

        // 기존 정보 유무 체크
        if (useReqOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        UseReq existUseReq = useReqOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        UseReq saveUseReq = this.useReqService.update(useReqUpdateDto, existUseReq);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UseReqResource useReqResource = new UseReqResource(saveUseReq);
        useReqResource.add(new Link("/docs/index.html#resources-useReq-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(useReqResource);
    }


}



