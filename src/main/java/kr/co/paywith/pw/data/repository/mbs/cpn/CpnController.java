package kr.co.paywith.pw.data.repository.mbs.cpn;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.cpn.*;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/cpn")
@Api(value = "CpnController", description = "쿠폰 API", basePath = "/api/cpn")
public class CpnController extends CommonController  {

    @Autowired
    CpnRepository cpnRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CpnValidator cpnValidator;

    @Autowired
    CpnService cpnService;

//    /**
//     * 정보 등록
//     */
//    @PostMapping
//    public ResponseEntity createCpn(
//            @RequestBody @Valid CpnDto cpnDto,
//            Errors errors,
//            @CurrentUser Account currentUser) {
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//
//        // 입력값 체크
//        cpnValidator.validate(cpnDto, errors);
//        if (errors.hasErrors()) {
//            return badRequest(errors);
//        }
//
//
//        // 입력값을 브랜드 객채에 대입
//        Cpn cpn = modelMapper.map(cpnDto, Cpn.class);
//
//        // 레코드 등록
//        Cpn newCpn = cpnService.create(cpn);
//
//        ControllerLinkBuilder selfLinkBuilder = linkTo(CpnController.class).slash(newCpn.getId());
//
//        URI createdUri = selfLinkBuilder.toUri();
//        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//        CpnResource cpnResource = new CpnResource(newCpn);
//        cpnResource.add(linkTo(CpnController.class).withRel("query-cpn"));
//        cpnResource.add(selfLinkBuilder.withRel("update-cpn"));
//        cpnResource.add(new Link("/docs/index.html#resources-cpn-create").withRel("profile"));
//
//        return ResponseEntity.created(createdUri).body(cpnResource);
//    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getCpns(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Cpn> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QCpn qCpn = QCpn.cpn;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qCpn.id.eq(searchForm.getId()));
        }

        Page<Cpn> page = this.cpnRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new CpnResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-cpns-list").withRel("profile"));
        pagedResources.add(linkTo(CpnController.class).withRel("create-cpn"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getCpn(@PathVariable  Integer id,
                                 @ModelAttribute SearchForm searchForm,
                                 Errors errors,
                                 @CurrentUser Account currentUser) {

        Optional<Cpn> cpnOptional = this.cpnRepository.findById(id);

        // 고객 정보 체크
        if (cpnOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cpn cpn = cpnOptional.get();

        // 유저 체크
        cpnValidator.validate(currentUser, cpn, errors);

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        if (currentUser.getAccountId() != null
                && currentUser.getAccountId().equalsIgnoreCase(cpn.getUserInfo().getUserId())) {
            // 회원이 쿠폰을 열람할 때 로직
            if (!cpn.getReadFl()) { // 열람 여부 확인 후 true로 변경
                // 처음 쿠폰을 열람하면 쿠폰번호를 생성한다
                if (StringUtils.isEmpty(cpn.getCpnNo())) {
                    cpn.setCpnNo(cpnService.getCpnNo(cpn));
                }
                cpn.setReadFl(true);
                cpnRepository.save(cpn);
            }
        }

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnResource cpnResource = new CpnResource(cpn);
        cpnResource.add(new Link("/docs/index.html#resources-cpn-get").withRel("profile"));

        return ResponseEntity.ok(cpnResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putCpn(@PathVariable Integer id,
                                   @RequestBody @Valid CpnUpdateDto cpnUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        if (currentUser.getAdmin() == null ||
            currentUser.getAdmin().getId() == null) {
            // 회원은 쿠폰 정보를 업데이트 할 수 없음.
            // 관리자는 만료 처리가 가능
            errors.reject("권한 없음", "쿠폰 정보 수정 권한이 없습니다");
        }

        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.cpnValidator.validate(cpnUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<Cpn> cpnOptional = this.cpnRepository.findById(id);

        // 기존 정보 유무 체크
        if (cpnOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        Cpn existCpn = cpnOptional.get();

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Cpn saveCpn = this.cpnService.update(cpnUpdateDto, existCpn);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnResource cpnResource = new CpnResource(saveCpn);
        cpnResource.add(new Link("/docs/index.html#resources-cpn-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(cpnResource);
    }




}



