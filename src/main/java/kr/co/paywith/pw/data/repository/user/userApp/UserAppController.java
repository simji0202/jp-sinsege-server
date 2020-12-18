package kr.co.paywith.pw.data.repository.user.userApp;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.user.userApp.*;
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
@RequestMapping(value = "/api/userApp")
@Api(value = "UserAppController", description = " 등급  API", basePath = "/api/userApp")
public class UserAppController extends CommonController {

    @Autowired
    UserAppRepository userAppRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserAppValidator userAppValidator;

    @Autowired
    UserAppService userAppService;


    /**
     *  정보 등록
     */
    @PostMapping
    public ResponseEntity createUserApp(
            @RequestBody @Valid UserAppDto userAppDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        userAppValidator.validate(userAppDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값 대입
        UserApp userApp = this.modelMapper.map(userAppDto, UserApp.class);

        // 레코드 등록
        UserApp newUserApp = userAppService.create(userApp);

        ControllerLinkBuilder selfLinkBuilder = linkTo(UserAppController.class).slash(newUserApp.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UserAppResource userAppResource = new UserAppResource(newUserApp);
        userAppResource.add(linkTo(UserAppController.class).withRel("query-userApp"));
        userAppResource.add(selfLinkBuilder.withRel("update-userApp"));
        userAppResource.add(new Link("/docs/index.html#resources-userApp-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(userAppResource);
    }


    /**
     * 페이지별 정보 취득
     */
    @GetMapping
    public ResponseEntity getUserApps(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<UserApp> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QUserApp qUserApp = QUserApp.userApp;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qUserApp.id.eq(searchForm.getId()));
        }


        Page<UserApp> page = this.userAppRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new UserAppResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-userApps-list").withRel("profile"));
        pagedResources.add(linkTo(UserAppController.class).withRel("create-userApp"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * id를 이용한 정보 취득
     */
    @GetMapping("/{id}")
    public ResponseEntity getUserApp(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<UserApp> userAppOptional = this.userAppRepository.findById(id);

        // 고객 정보 체크
        if (userAppOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserApp userApp = userAppOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UserAppResource userAppResource = new UserAppResource(userApp);
        userAppResource.add(new Link("/docs/index.html#resources-userApp-get").withRel("profile"));

        return ResponseEntity.ok(userAppResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putUserApp(@PathVariable Integer id,
                                   @RequestBody @Valid UserAppUpdateDto userAppUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.userAppValidator.validate(userAppUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<UserApp> userAppOptional = this.userAppRepository.findById(id);

        // 기존 정보 유무 체크
        if (userAppOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        UserApp existUserApp = userAppOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        UserApp saveUserApp = this.userAppService.update(userAppUpdateDto, existUserApp);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UserAppResource userAppResource = new UserAppResource(saveUserApp);
        userAppResource.add(new Link("/docs/index.html#resources-userApp-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(userAppResource);
    }


}



