package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

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
@RequestMapping(value = "/api/mrhstTrmnl")
@Api(value = "MrhstTrmnlController", description = "가맹점 로그인(단말기) API", basePath = "/api/mrhstTrmnl")
public class MrhstTrmnlController extends CommonController {

    @Autowired
    MrhstTrmnlRepository mrhstTrmnlRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MrhstTrmnlValidator mrhstTrmnlValidator;

    @Autowired
    MrhstTrmnlService mrhstTrmnlService;

    @PostMapping
    public ResponseEntity createMrhstTrmnl(
            @RequestBody @Valid MrhstTrmnlDto mrhstTrmnlDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        mrhstTrmnlValidator.validate(mrhstTrmnlDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        MrhstTrmnl mrhstTrmnl = modelMapper.map(mrhstTrmnlDto, MrhstTrmnl.class);


        if (currentUser != null) {
            // 등록자 및 변경자  정보 저장
            mrhstTrmnl.setCreateBy(currentUser.getAccountId());
            mrhstTrmnl.setUpdateBy(currentUser.getAccountId());
        }

        // 레코드 등록
        MrhstTrmnl newMrhstTrmnl = mrhstTrmnlService.create(mrhstTrmnl);

        ControllerLinkBuilder selfLinkBuilder = linkTo(MrhstTrmnlController.class).slash(newMrhstTrmnl.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        MrhstTrmnlResource mrhstTrmnlResource = new MrhstTrmnlResource(newMrhstTrmnl);
        mrhstTrmnlResource.add(linkTo(MrhstTrmnlController.class).withRel("query-mrhstTrmnl"));
        mrhstTrmnlResource.add(selfLinkBuilder.withRel("update-mrhstTrmnl"));
        mrhstTrmnlResource.add(new Link("/docs/index.html#resources-mrhstTrmnl-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(mrhstTrmnlResource);
    }


    @GetMapping
    public ResponseEntity getMrhstTrmnls(SearchForm searchForm,
                                         Pageable pageable,
                                         PagedResourcesAssembler<MrhstTrmnl> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QMrhstTrmnl qMrhstTrmnl = QMrhstTrmnl.mrhstTrmnl;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qMrhstTrmnl.id.eq(searchForm.getId()));
        }


        Page<MrhstTrmnl> page = this.mrhstTrmnlRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new MrhstTrmnlResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-mrhstTrmnls-list").withRel("profile"));
        pagedResources.add(linkTo(MrhstTrmnlController.class).withRel("create-mrhstTrmnl"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity getMrhstTrmnl(@PathVariable Integer id,
                                        @CurrentUser Account currentUser) {

        Optional<MrhstTrmnl> mrhstTrmnlOptional = this.mrhstTrmnlRepository.findById(id);

        // 고객 정보 체크
        if (mrhstTrmnlOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MrhstTrmnl mrhstTrmnl = mrhstTrmnlOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        MrhstTrmnlResource mrhstTrmnlResource = new MrhstTrmnlResource(mrhstTrmnl);
        mrhstTrmnlResource.add(new Link("/docs/index.html#resources-mrhstTrmnl-get").withRel("profile"));

        return ResponseEntity.ok(mrhstTrmnlResource);
    }


    /**
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity putMrhstTrmnl(@PathVariable Integer id,
                                        @RequestBody @Valid MrhstTrmnlUpdateDto mrhstTrmnlUpdateDto,
                                        Errors errors,
                                        @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.mrhstTrmnlValidator.validate(mrhstTrmnlUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<MrhstTrmnl> mrhstTrmnlOptional = this.mrhstTrmnlRepository.findById(id);

        // 기존 정보 유무 체크
        if (mrhstTrmnlOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        MrhstTrmnl existMrhstTrmnl = mrhstTrmnlOptional.get();

        if (currentUser != null) {
            // 변경자 정보 저장
            existMrhstTrmnl.setUpdateBy(currentUser.getAccountId());
        }

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        MrhstTrmnl saveMrhstTrmnl = this.mrhstTrmnlService.update(mrhstTrmnlUpdateDto, existMrhstTrmnl);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        MrhstTrmnlResource mrhstTrmnlResource = new MrhstTrmnlResource(saveMrhstTrmnl);
        mrhstTrmnlResource.add(new Link("/docs/index.html#resources-mrhstTrmnl-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(mrhstTrmnlResource);
    }


    /**
     * 암호 변경
     */
    @PutMapping("/updatePw/{id}")
    public ResponseEntity putMrhstTrmnlChangeMrhstTrmnlPw(@PathVariable Integer id,
                                                          @RequestBody @Valid MrhstTrmnlPwUpdateDto mrhstTrmnlPwUpdateDto,
                                                          Errors errors,
                                                          @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.mrhstTrmnlValidator.validate(mrhstTrmnlPwUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Optional<MrhstTrmnl> mrhstTrmnlOptional = this.mrhstTrmnlRepository.findById(id);

        if (mrhstTrmnlOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        MrhstTrmnl existMrhstTrmnl = mrhstTrmnlOptional.get();


        // 패스워드 설정
        existMrhstTrmnl.setUserPw(mrhstTrmnlPwUpdateDto.getUserPw());

        //  변경자 정보 설정
        if (currentUser != null) {
            existMrhstTrmnl.setUpdateBy(currentUser.getAccountId());
        }

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        MrhstTrmnl saveMrhstTrmnl = this.mrhstTrmnlService.updatePw(existMrhstTrmnl);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        MrhstTrmnlResource mrhstTrmnlResource = new MrhstTrmnlResource(saveMrhstTrmnl);
        mrhstTrmnlResource.add(new Link("/docs/index.html#resources-mrhstTrmnl-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(mrhstTrmnlResource);

    }

}