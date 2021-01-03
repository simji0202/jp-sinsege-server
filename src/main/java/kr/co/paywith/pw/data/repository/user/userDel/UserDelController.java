package kr.co.paywith.pw.data.repository.user.userDel;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.user.userDel.*;
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
@RequestMapping(value = "/api/userDel")
@Api(value = "UserDelController", description = " 등급  API", basePath = "/api/userDel")
public class UserDelController extends CommonController {

    @Autowired
    UserDelRepository userDelRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDelValidator userDelValidator;

    @Autowired
    UserDelService userDelService;


    // kms: userInfo 데이터를 사용해서 서버 내부에서 저장하므로 POST, PUT API 불필요
//    /**
//     *  정보 등록
//     */
//    @PostMapping
//    public ResponseEntity createUserDel(
//            @RequestBody @Valid UserDelDto userDelDto,
//            Errors errors,
//            @CurrentUser Account currentUser) {
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//
//        // 입력값 체크
//        userDelValidator.validate(userDelDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 입력값 대입
//        UserDel userDel = this.modelMapper.map(userDelDto, UserDel.class);
//
//        // 레코드 등록
//        UserDel newUserDel = userDelService.create(userDel);
//
//        ControllerLinkBuilder selfLinkBuilder = linkTo(UserDelController.class).slash(newUserDel.getId());
//
//        URI createdUri = selfLinkBuilder.toUri();
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        UserDelResource userDelResource = new UserDelResource(newUserDel);
//        userDelResource.add(linkTo(UserDelController.class).withRel("query-userDel"));
//        userDelResource.add(selfLinkBuilder.withRel("update-userDel"));
//        userDelResource.add(new Link("/docs/index.html#resources-userDel-create").withRel("profile"));
//
//        return ResponseEntity.created(createdUri).body(userDelResource);
//    }


    /**
     * 페이지별 정보 취득
     */
    @GetMapping
    public ResponseEntity getUserDels(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<UserDel> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QUserDel qUserDel = QUserDel.userDel;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qUserDel.id.eq(searchForm.getId()));
        }


        Page<UserDel> page = this.userDelRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new UserDelResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-userDels-list").withRel("profile"));
        pagedResources.add(linkTo(UserDelController.class).withRel("create-userDel"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * id를 이용한 정보 취득
     */
    @GetMapping("/{id}")
    public ResponseEntity getUserDel(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<UserDel> userDelOptional = this.userDelRepository.findById(id);

        // 고객 정보 체크
        if (userDelOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserDel userDel = userDelOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        UserDelResource userDelResource = new UserDelResource(userDel);
        userDelResource.add(new Link("/docs/index.html#resources-userDel-get").withRel("profile"));

        return ResponseEntity.ok(userDelResource);
    }

// kms: userInfo 데이터를 사용해서 서버 내부에서 저장하므로 POST, PUT API 불필요
//    /**
//     * 정보 변경
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity putUserDel(@PathVariable Integer id,
//                                   @RequestBody @Valid UserDelUpdateDto userDelUpdateDto,
//                                   Errors errors,
//                                   @CurrentUser Account currentUser) {
//        // 입력체크
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 논리적 오류 (제약조건) 체크
//        this.userDelValidator.validate(userDelUpdateDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//        // 기존 테이블에서 관련 정보 취득
//        Optional<UserDel> userDelOptional = this.userDelRepository.findById(id);
//
//        // 기존 정보 유무 체크
//        if (userDelOptional.isEmpty()) {
//            // 404 Error return
//            return ResponseEntity.notFound().build();
//        }
//
//        // 기존 정보 취득
//        UserDel existUserDel = userDelOptional.get();
//
//        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//        UserDel saveUserDel = this.userDelService.update(userDelUpdateDto, existUserDel);
//
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        UserDelResource userDelResource = new UserDelResource(saveUserDel);
//        userDelResource.add(new Link("/docs/index.html#resources-userDel-update").withRel("profile"));
//
//        // 정상적 처리
//        return ResponseEntity.ok(userDelResource);
//    }


}



