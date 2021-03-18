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
@RequestMapping(value = "/api/grade")
@Api(value = "GradeController", description = " 등급  API", basePath = "/api/grade")
public class GradeController extends CommonController {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GradeValidator gradeValidator;

    @Autowired
    GradeService gradeService;


    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createGrade(
            @RequestBody @Valid GradeDto gradeDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        gradeValidator.validate(gradeDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값 대입
        Grade grade = this.modelMapper.map(gradeDto, Grade.class);

        // 레코드 등록
        Grade newGrade = gradeService.create(grade);

        ControllerLinkBuilder selfLinkBuilder = linkTo(GradeController.class).slash(newGrade.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GradeResource gradeResource = new GradeResource(newGrade);
        gradeResource.add(linkTo(GradeController.class).withRel("query-grade"));
        gradeResource.add(selfLinkBuilder.withRel("update-grade"));
        gradeResource.add(new Link("/docs/index.html#resources-grade-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(gradeResource);
    }


    /**
     * 페이지별 정보 취득
     */
    @GetMapping
    public ResponseEntity getGrades(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Grade> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QGrade qGrade = QGrade.grade;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qGrade.id.eq(searchForm.getId()));
        }


        Page<Grade> page = this.gradeRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new GradeResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-grades-list").withRel("profile"));
        pagedResources.add(linkTo(GradeController.class).withRel("create-grade"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보 취득
     */
    @GetMapping("/{id}")
    public ResponseEntity getGrade(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<Grade> gradeOptional = this.gradeRepository.findById(id);

        // 고객 정보 체크
        if (gradeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Grade grade = gradeOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GradeResource gradeResource = new GradeResource(grade);
        gradeResource.add(new Link("/docs/index.html#resources-grade-get").withRel("profile"));

        return ResponseEntity.ok(gradeResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putGrade(@PathVariable Integer id,
                                   @RequestBody @Valid GradeUpdateDto gradeUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.gradeValidator.validate(gradeUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<Grade> gradeOptional = this.gradeRepository.findById(id);

        // 기존 정보 유무 체크
        if (gradeOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        Grade existGrade = gradeOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Grade saveGrade = this.gradeService.update(gradeUpdateDto, existGrade);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        GradeResource gradeResource = new GradeResource(saveGrade);
        gradeResource.add(new Link("/docs/index.html#resources-grade-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(gradeResource);
    }


}



