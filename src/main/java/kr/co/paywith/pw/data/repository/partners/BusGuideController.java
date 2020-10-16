package kr.co.paywith.pw.data.repository.partners;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminAdapter;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping(value = "/api/busGuide", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "BusGuideController", description = "여행사 업체 관리자 API", basePath = "/api/busGuide")
public class BusGuideController {

  @Autowired
  private BusGuideRepository busGuideRepository;

  @Autowired
  private BusGuideValidator busGuideValidator;
  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  private ResponseEntity insertBusGuide (@RequestBody @Valid BusGuideDto busGuideDto,
                                         Errors errors,
                                         @CurrentUser Admin currentUser){
    // 입력 값 체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    busGuideValidator.validate(busGuideDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    BusGuide busGuide = modelMapper.map(busGuideDto, BusGuide.class);

    if ( currentUser != null ) {
      busGuide.setCreateBy(currentUser.getAdminNm());
      busGuide.setUpdateBy(currentUser.getAdminNm());
    }

    BusGuide newBusGuide = this.busGuideRepository.save(busGuide);

    ControllerLinkBuilder selfLinkBuilder = linkTo(BusGuideController.class).slash(newBusGuide.getId());
    URI createdUri = selfLinkBuilder.toUri();

    BusGuideResource busGuideResource = new BusGuideResource(newBusGuide);
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    busGuideResource.add(linkTo(BusGuideController.class).withRel("query-busGuide"));
    busGuideResource.add(selfLinkBuilder.withRel("update-busGuide"));
    busGuideResource.add(new Link("/docs/index.html#resources-busGuide-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(busGuideResource);
  }

  @GetMapping
  public ResponseEntity getBusGuides(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<BusGuide> assembler
          , @AuthenticationPrincipal AdminAdapter user
          , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QBusGuide qBusGuide = QBusGuide.busGuide;

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 파트너 아이디
    if (searchForm.getPartnersId() != null && searchForm.getPartnersId() > 0) {
      booleanBuilder.and(qBusGuide.partners.id.eq(searchForm.getPartnersId()));
    }

     // 가이드 이름
    if (searchForm.getGuideName() != null ) {
      booleanBuilder.and(qBusGuide.guideName.containsIgnoreCase(searchForm.getGuideName()));
    }

//    // 회사 아이디
//    if (searchForm.getBusGuideId() != null && searchForm.getBusGuideId() > 0) {
//      booleanBuilder.and(qBusGuide.id.eq(searchForm.getBusGuideId()));
//    }
//
//    // 검색조건 업체명
//    if (searchForm.getAdminNm() != null) {
//      booleanBuilder.and(qBusGuide.busGuideNm.containsIgnoreCase(searchForm.getAdminNm()));
//    }


    Page<BusGuide> page = this.busGuideRepository.findAll(booleanBuilder, pageable);

    var pagedResources = assembler.toResource(page, e -> new BusGuideResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-busGuides-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(BusGuideController.class).withRel("create-busGuide"));
    }
    return ResponseEntity.ok(pagedResources);
  }

  @GetMapping("/{id}")
  public ResponseEntity getBusGuide(@PathVariable Integer id,
                                   @CurrentUser Admin currentUser) {

    Optional<BusGuide> busGuideOptional = this.busGuideRepository.findById(id);

    // 업체 정보 체크
    if (busGuideOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    BusGuide busGuide = busGuideOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    BusGuideResource busGuideResource = new BusGuideResource(busGuide);
    busGuideResource.add(new Link("/docs/index.html#resources-busGuide-get").withRel("profile"));

    return ResponseEntity.ok(busGuideResource);
  }




  @PutMapping("/{id}")
  public ResponseEntity putPartners(@PathVariable Integer id,
                                    @RequestBody @Valid BusGuide busGuide,
                                    Errors errors,
                                    @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<BusGuide> busGuideOptional = this.busGuideRepository.findById(id);

    if (busGuideOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    BusGuide saveBusGuide = this.busGuideRepository.save(busGuide);


    // 정상적 처리
    return ResponseEntity.ok(saveBusGuide);
  }


  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

}
