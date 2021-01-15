package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

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
@RequestMapping(value = "/api/cpnIssu")
@Api(value = "CpnIssuController", description = "쿠폰 발행 API", basePath = "/api/cpnIssu")
public class CpnIssuController extends CommonController {


    /**
     * 발급 규칙에 대한 정의 예정
     *
     *  1. 쿠폰의 발급은 반드시 cpnIssu 을 통해서 <---   o. x
     *  2. 쿠폰 발급과 동시에 쿠폰 번호 부여 여부  <---   o. x
     *
     */

    @Autowired
    CpnIssuRepository cpnIssuRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CpnIssuValidator cpnIssuValidator;

    @Autowired
    CpnIssuService cpnIssuService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createCpnIssu(
            @RequestBody @Valid CpnIssuDto cpnIssuDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        cpnIssuValidator.validate(cpnIssuDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        CpnIssu cpnIssu = modelMapper.map(cpnIssuDto, CpnIssu.class);

        // userIdList 길이만큼 user와 연결한 cpn 생성

        // 레코드 등록
        CpnIssu newCpnIssu = cpnIssuService.create(cpnIssu);

        ControllerLinkBuilder selfLinkBuilder = linkTo(CpnIssuController.class).slash(newCpnIssu.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnIssuResource cpnIssuResource = new CpnIssuResource(newCpnIssu);
        cpnIssuResource.add(linkTo(CpnIssuController.class).withRel("query-cpnIssu"));
        cpnIssuResource.add(selfLinkBuilder.withRel("update-cpnIssu"));
        cpnIssuResource.add(new Link("/docs/index.html#resources-cpnIssu-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(cpnIssuResource);
    }

    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getCpnIssus(SearchForm searchForm,
                                      Pageable pageable,
                                      PagedResourcesAssembler<CpnIssu> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QCpnIssu qCpnIssu = QCpnIssu.cpnIssu;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qCpnIssu.id.eq(searchForm.getId()));
        }


        Page<CpnIssu> page = this.cpnIssuRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new CpnIssuResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-cpnIssus-list").withRel("profile"));
        pagedResources.add(linkTo(CpnIssuController.class).withRel("create-cpnIssu"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getCpnIssu(@PathVariable Integer id,
                                     @CurrentUser Account currentUser) {

        Optional<CpnIssu> cpnIssuOptional = this.cpnIssuRepository.findById(id);

        // 고객 정보 체크
        if (cpnIssuOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CpnIssu cpnIssu = cpnIssuOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnIssuResource cpnIssuResource = new CpnIssuResource(cpnIssu);
        cpnIssuResource.add(new Link("/docs/index.html#resources-cpnIssu-get").withRel("profile"));

        return ResponseEntity.ok(cpnIssuResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putCpnIssu(@PathVariable Integer id,
                                     @RequestBody @Valid CpnIssuUpdateDto cpnIssuUpdateDto,
                                     Errors errors,
                                     @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.cpnIssuValidator.validate(cpnIssuUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<CpnIssu> cpnIssuOptional = this.cpnIssuRepository.findById(id);

        // 기존 정보 유무 체크
        if (cpnIssuOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        CpnIssu existCpnIssu = cpnIssuOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        CpnIssu saveCpnIssu = this.cpnIssuService.update(cpnIssuUpdateDto, existCpnIssu);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnIssuResource cpnIssuResource = new CpnIssuResource(saveCpnIssu);
        cpnIssuResource.add(new Link("/docs/index.html#resources-cpnIssu-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(cpnIssuResource);
    }

}



