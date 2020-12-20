package kr.co.paywith.pw.data.repository.mbs.cpnMaster;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.*;
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
@RequestMapping(value = "/api/cpnMaster")
@Api(value = "CpnMasterController", description = "쿠폰 마스터 API", basePath = "/api/cpnMaster")
public class CpnMasterController extends CommonController {

    @Autowired
    CpnMasterRepository cpnMasterRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CpnMasterValidator cpnMasterValidator;

    @Autowired
    CpnMasterService cpnMasterService;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createCpnMaster(
            @RequestBody @Valid CpnMasterDto cpnMasterDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값 체크
        cpnMasterValidator.validate(cpnMasterDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        CpnMaster cpnMaster = modelMapper.map(cpnMasterDto, CpnMaster.class);

        // 레코드 등록
        CpnMaster newCpnMaster = cpnMasterService.create(cpnMaster);

        ControllerLinkBuilder selfLinkBuilder = linkTo(CpnMasterController.class).slash(newCpnMaster.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnMasterResource cpnMasterResource = new CpnMasterResource(newCpnMaster);
        cpnMasterResource.add(linkTo(CpnMasterController.class).withRel("query-cpnMaster"));
        cpnMasterResource.add(selfLinkBuilder.withRel("update-cpnMaster"));
        cpnMasterResource.add(new Link("/docs/index.html#resources-cpnMaster-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(cpnMasterResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getCpnMasters(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<CpnMaster> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QCpnMaster qCpnMaster = QCpnMaster.cpnMaster;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qCpnMaster.id.eq(searchForm.getId()));
        }


        Page<CpnMaster> page = this.cpnMasterRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new CpnMasterResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-cpnMasters-list").withRel("profile"));
        pagedResources.add(linkTo(CpnMasterController.class).withRel("create-cpnMaster"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     * 정보취득 (1건 )
     */
    @GetMapping("/{id}")
    public ResponseEntity getCpnMaster(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {

        Optional<CpnMaster> cpnMasterOptional = this.cpnMasterRepository.findById(id);

        // 고객 정보 체크
        if (cpnMasterOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CpnMaster cpnMaster = cpnMasterOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnMasterResource cpnMasterResource = new CpnMasterResource(cpnMaster);
        cpnMasterResource.add(new Link("/docs/index.html#resources-cpnMaster-get").withRel("profile"));

        return ResponseEntity.ok(cpnMasterResource);
    }


    /**
     * 정보 변경
     */
    @PutMapping("/{id}")
    public ResponseEntity putCpnMaster(@PathVariable Integer id,
                                   @RequestBody @Valid CpnMasterUpdateDto cpnMasterUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.cpnMasterValidator.validate(cpnMasterUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 기존 테이블에서 관련 정보 취득
        Optional<CpnMaster> cpnMasterOptional = this.cpnMasterRepository.findById(id);

        // 기존 정보 유무 체크
        if (cpnMasterOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // 기존 정보 취득
        CpnMaster existCpnMaster = cpnMasterOptional.get();


        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        CpnMaster saveCpnMaster = this.cpnMasterService.update(cpnMasterUpdateDto, existCpnMaster);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        CpnMasterResource cpnMasterResource = new CpnMasterResource(saveCpnMaster);
        cpnMasterResource.add(new Link("/docs/index.html#resources-cpnMaster-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(cpnMasterResource);
    }





}



