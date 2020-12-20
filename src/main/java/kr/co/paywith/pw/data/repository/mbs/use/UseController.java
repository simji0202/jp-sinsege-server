package kr.co.paywith.pw.data.repository.mbs.use;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.use.*;
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
@RequestMapping(value = "/api/use")
@Api(value = "UseController", description = "사용처리 API", basePath = "/api/use")
public class UseController extends CommonController {

    @Autowired
    UseRepository useRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UseValidator useValidator;

    @Autowired
    UseService useService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createUse(
            @RequestBody @Valid UseDto useDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        useValidator.validate(useDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        Use use = modelMapper.map(useDto, Use.class);

        // 레코드 등록
        Use newUse = useService.create(use);

        ControllerLinkBuilder selfLinkBuilder = linkTo(UseController.class).slash(newUse.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UseResource useResource = new UseResource(newUse);
        useResource.add(linkTo(UseController.class).withRel("query-use"));
        useResource.add(selfLinkBuilder.withRel("update-use"));
        useResource.add(new Link("/docs/index.html#resources-use-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(useResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getUses(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Use> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QUse qUse = QUse.use;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qUse.id.eq(searchForm.getUseId()));
        }


        Page<Use> page = this.useRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new UseResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-uses-list").withRel("profile"));
        pagedResources.add(linkTo(UseController.class).withRel("create-use"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getUse(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<Use> useOptional = this.useRepository.findById(id);

        // 고객 정보 체크
        if (useOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Use use = useOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UseResource useResource = new UseResource(use);
        useResource.add(new Link("/docs/index.html#resources-use-get").withRel("profile"));

        return ResponseEntity.ok(useResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putUse(@PathVariable Integer id,
                                   @RequestBody @Valid UseUpdateDto useUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.useValidator.validate(useUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<Use> useOptional = this.useRepository.findById(id);

        // 기존 정보 유무 체크
        if (useOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        Use existUse = useOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Use saveUse = this.useService.update(useUpdateDto, existUse);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UseResource useResource = new UseResource(saveUse);
        useResource.add(new Link("/docs/index.html#resources-use-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(useResource);
    }





}



