package kr.co.paywith.pw.data.repository.mbs.bbs;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.bbs.*;
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
@RequestMapping(value = "/api/bbs")
@Api(value = "BbsController", description = "게시판  API", basePath = "/api/bbs")
public class BbsController extends CommonController {

    @Autowired
    BbsRepository bbsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BbsValidator bbsValidator;

    @Autowired
    BbsService bbsService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createBbs(
            @RequestBody @Valid BbsDto bbsDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        bbsValidator.validate(bbsDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        Bbs bbs = modelMapper.map(bbsDto, Bbs.class);

        // 레코드 등록
        Bbs newBbs = bbsService.create(bbs);

        ControllerLinkBuilder selfLinkBuilder = linkTo(BbsController.class).slash(newBbs.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BbsResource bbsResource = new BbsResource(newBbs);
        bbsResource.add(linkTo(BbsController.class).withRel("query-bbs"));
        bbsResource.add(selfLinkBuilder.withRel("update-bbs"));
        bbsResource.add(new Link("/docs/index.html#resources-bbs-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(bbsResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getBbss(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Bbs> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QBbs qBbs = QBbs.bbs;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qBbs.id.eq(searchForm.getId()));
        }


        Page<Bbs> page = this.bbsRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new BbsResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-bbss-list").withRel("profile"));
        pagedResources.add(linkTo(BbsController.class).withRel("create-bbs"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getBbs(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<Bbs> bbsOptional = this.bbsRepository.findById(id);

        // 고객 정보 체크
        if (bbsOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Bbs bbs = bbsOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BbsResource bbsResource = new BbsResource(bbs);
        bbsResource.add(new Link("/docs/index.html#resources-bbs-get").withRel("profile"));

        return ResponseEntity.ok(bbsResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putBbs(@PathVariable Integer id,
                                   @RequestBody @Valid BbsUpdateDto bbsUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.bbsValidator.validate(bbsUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<Bbs> bbsOptional = this.bbsRepository.findById(id);

        // 기존 정보 유무 체크
        if (bbsOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        Bbs existBbs = bbsOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Bbs saveBbs = this.bbsService.update(bbsUpdateDto, existBbs);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BbsResource bbsResource = new BbsResource(saveBbs);
        bbsResource.add(new Link("/docs/index.html#resources-bbs-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(bbsResource);
    }
}



