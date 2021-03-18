package kr.co.paywith.pw.data.repository.user.grade;

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
@RequestMapping(value = "/api/gradeUp")
@Api(value = "GradeUpController", description = " 등급  API", basePath = "/api/gradeUp")
public class GradeUpController extends CommonController {

    @Autowired
    GradeUpRepository gradeUpRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GradeUpValidator gradeUpValidator;

    @Autowired
    GradeUpService gradeUpService;


    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createGradeUp(
            @RequestBody @Valid GradeUpDto gradeUpDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        gradeUpValidator.validate(gradeUpDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값 대입
        GradeUp gradeUp = this.modelMapper.map(gradeUpDto, GradeUp.class);

        // 레코드 등록
        GradeUp newGradeUp = gradeUpService.create(gradeUp);

        ControllerLinkBuilder selfLinkBuilder = linkTo(GradeUpController.class).slash(newGradeUp.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GradeUpResource gradeUpResource = new GradeUpResource(newGradeUp);
        gradeUpResource.add(linkTo(GradeUpController.class).withRel("query-gradeUp"));
        gradeUpResource.add(selfLinkBuilder.withRel("update-gradeUp"));
        gradeUpResource.add(new Link("/docs/index.html#resources-gradeUp-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(gradeUpResource);
    }


    /**
     * 페이지별 정보 취득
     */
    @GetMapping
    public ResponseEntity getGradeUps(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<GradeUp> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QGradeUp qGradeUp = QGradeUp.gradeUp;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qGradeUp.id.eq(searchForm.getId()));
        }


        Page<GradeUp> page = this.gradeUpRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new GradeUpResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-gradeUps-list").withRel("profile"));
        pagedResources.add(linkTo(GradeUpController.class).withRel("create-gradeUp"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보 취득
     */
    @GetMapping("/{id}")
    public ResponseEntity getGradeUp(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<GradeUp> gradeUpOptional = this.gradeUpRepository.findById(id);

        // 고객 정보 체크
        if (gradeUpOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        GradeUp gradeUp = gradeUpOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GradeUpResource gradeUpResource = new GradeUpResource(gradeUp);
        gradeUpResource.add(new Link("/docs/index.html#resources-gradeUp-get").withRel("profile"));

        return ResponseEntity.ok(gradeUpResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putGradeUp(@PathVariable Integer id,
                                   @RequestBody @Valid GradeUpUpdateDto gradeUpUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.gradeUpValidator.validate(gradeUpUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<GradeUp> gradeUpOptional = this.gradeUpRepository.findById(id);

        // 기존 정보 유무 체크
        if (gradeUpOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        GradeUp existGradeUp = gradeUpOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        GradeUp saveGradeUp = this.gradeUpService.update(gradeUpUpdateDto, existGradeUp);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GradeUpResource gradeUpResource = new GradeUpResource(saveGradeUp);
        gradeUpResource.add(new Link("/docs/index.html#resources-gradeUp-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(gradeUpResource);
    }


}



