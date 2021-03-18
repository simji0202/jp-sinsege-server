package kr.co.paywith.pw.data.repository.code.addrCode;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/addrCode")
@Api(value = "AddrCodeController", description = "주소 코드  API", basePath = "/api/addrCode")
public class AddrCodeController extends CommonController {

    @Autowired
    AddrCodeRepository addrCodeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddrCodeValidator addrCodeValidator;

    /**
     * 정보 등록
     */
    @PostMapping
    public ResponseEntity createAddrCode(
            @RequestBody @Valid AddrCodeDto addrCodeDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값 체크
        addrCodeValidator.validate(currentUser, addrCodeDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        AddrCode addrCode = modelMapper.map(addrCodeDto, AddrCode.class);

        // 레코드 등록
        AddrCode newAddrCode = addrCodeRepository.save(addrCode);

        ControllerLinkBuilder selfLinkBuilder = linkTo(AddrCodeController.class).slash(newAddrCode.getCd());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        AddrCodeResource addrCodeResource = new AddrCodeResource(newAddrCode);
        addrCodeResource.add(linkTo(AddrCodeController.class).withRel("query-addrCode"));
        addrCodeResource.add(selfLinkBuilder.withRel("update-addrCode"));
        addrCodeResource.add(new Link("/docs/index.html#resources-addrCode-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(addrCodeResource);
    }


    /**
     * 정보취득 (조건별 page )
     */
    @GetMapping
    public ResponseEntity getAddrCodes(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<AddrCode> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QAddrCode qAddrCode = QAddrCode.addrCode;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 코드
        if (searchForm.getCd() != null) {
            booleanBuilder.and(qAddrCode.cd.eq(searchForm.getCd()));
        }

        // 길이
        if (searchForm.getLength() != null) {
          booleanBuilder.and(qAddrCode.cd.length().eq(searchForm.getLength()));
        }

        // 시작 문자열
        if (searchForm.getS() != null) {
          booleanBuilder.and(qAddrCode.cd.startsWith(searchForm.getS()));
        }

        // 종료 문자열
        if (searchForm.getE() != null) {
          booleanBuilder.and(qAddrCode.cd.endsWith(searchForm.getE()));
        }

        Page<AddrCode> page = this.addrCodeRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new AddrCodeResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-addrCodes-list").withRel("profile"));
        pagedResources.add(linkTo(AddrCodeController.class).withRel("create-addrCode"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity removeAddrCode(@PathVariable String id,
//        @RequestBody(required = false) AddrCodeDeleteDto addrCodeDeleteDto,
//        Errors errors,
        @CurrentUser Account currentUser) {

        Optional<AddrCode> addrCodeOptional = this.addrCodeRepository.findById(id);

        if (addrCodeOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        // TODO 삭제 권한 확인
        if (currentUser.getAdmin() != null) {
            // 404 Error return
            return ResponseEntity.badRequest().build();
        }

        AddrCode addrCode = addrCodeOptional.get();
        addrCodeRepository.delete(addrCode);

        // 정상적 처리
        return ResponseEntity.ok().build();
    }
}



