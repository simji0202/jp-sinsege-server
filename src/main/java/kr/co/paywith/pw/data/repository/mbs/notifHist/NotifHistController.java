package kr.co.paywith.pw.data.repository.mbs.notifHist;

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
@RequestMapping(value = "/api/notifHist")
@Api(value = "NotifHistController", description = "쿠폰 API", basePath = "/api/notifHist")
public class NotifHistController extends CommonController {

    @Autowired
    NotifHistRepository notifHistRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    NotifHistValidator notifHistValidator;

    @Autowired
    NotifHistService notifHistService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createNotifHist(
            @RequestBody @Valid NotifHistDto notifHistDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        notifHistValidator.validate(notifHistDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        NotifHist notifHist = modelMapper.map(notifHistDto, NotifHist.class);

        // 레코드 등록
        NotifHist newNotifHist = notifHistService.create(notifHist);

        ControllerLinkBuilder selfLinkBuilder = linkTo(NotifHistController.class).slash(newNotifHist.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        NotifHistResource notifHistResource = new NotifHistResource(newNotifHist);
        notifHistResource.add(linkTo(NotifHistController.class).withRel("query-notifHist"));
        notifHistResource.add(selfLinkBuilder.withRel("update-notifHist"));
        notifHistResource.add(new Link("/docs/index.html#resources-notifHist-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(notifHistResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getNotifHists(SearchForm searchForm,
                                        Pageable pageable,
                                        PagedResourcesAssembler<NotifHist> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

        QNotifHist qNotifHist = QNotifHist.notifHist;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qNotifHist.id.eq(searchForm.getId()));
        }


        Page<NotifHist> page = this.notifHistRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new NotifHistResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-notifHists-list").withRel("profile"));
        pagedResources.add(linkTo(NotifHistController.class).withRel("create-notifHist"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getNotifHist(@PathVariable Integer id,
                                       @CurrentUser Account currentUser) {

        Optional<NotifHist> notifHistOptional = this.notifHistRepository.findById(id);

        // 고객 정보 체크
        if (notifHistOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        NotifHist notifHist = notifHistOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        NotifHistResource notifHistResource = new NotifHistResource(notifHist);
        notifHistResource.add(new Link("/docs/index.html#resources-notifHist-get").withRel("profile"));

        return ResponseEntity.ok(notifHistResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putNotifHist(@PathVariable Integer id,
                                       @RequestBody @Valid NotifHistUpdateDto notifHistUpdateDto,
                                       Errors errors,
                                       @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.notifHistValidator.validate(notifHistUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<NotifHist> notifHistOptional = this.notifHistRepository.findById(id);

        // 기존 정보 유무 체크
        if (notifHistOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        NotifHist existNotifHist = notifHistOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        NotifHist saveNotifHist = this.notifHistService.update(notifHistUpdateDto, existNotifHist);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        NotifHistResource notifHistResource = new NotifHistResource(saveNotifHist);
        notifHistResource.add(new Link("/docs/index.html#resources-notifHist-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(notifHistResource);
    }
}
