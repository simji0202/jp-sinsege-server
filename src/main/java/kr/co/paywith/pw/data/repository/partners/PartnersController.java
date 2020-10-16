package kr.co.paywith.pw.data.repository.partners;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;

import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.common.ExcelConstant;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminAdapter;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.requests.RequestsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping(value = "/api/partners", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "PartnersController", description = "여행사 업체 관리자 API", basePath = "/api/partners")
public class PartnersController {

  @Autowired
  private PartnersRepository partnersRepository;

  @Autowired
  private PartnersValidator partnersValidator;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PartnersService partnersService;

  @PostMapping
  private ResponseEntity insertPartners (@RequestBody @Valid PartnersDto partnerDto,
                                        Errors errors,
                                        @CurrentUser Admin currentUser){
    // 입력 값 체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    partnersValidator.validate(partnerDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Partners newPartner = this.partnersService.createPartners(partnerDto, currentUser);

    ControllerLinkBuilder selfLinkBuilder = linkTo(PartnersController.class).slash(newPartner.getId());
    URI createdUri = selfLinkBuilder.toUri();

    PartnersResource partnerResource = new PartnersResource(newPartner);
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    partnerResource.add(linkTo(PartnersController.class).withRel("query-partner"));
    partnerResource.add(selfLinkBuilder.withRel("update-partner"));
    partnerResource.add(new Link("/docs/index.html#resources-partner-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(partnerResource);

  }


  @GetMapping
  public ResponseEntity getPartnerss(SearchForm searchForm,
                                     Pageable pageable,
                                     PagedResourcesAssembler<PartnersListDto> assembler
      , @AuthenticationPrincipal AdminAdapter user
      , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QPartners qPartners = QPartners.partners;

    BooleanBuilder booleanBuilder = new BooleanBuilder();


    // 회사 아이디
    if ( searchForm.getAdminId() != null ) {
      booleanBuilder.and(qPartners.adminId.containsIgnoreCase(searchForm.getAdminId()));
    }

    // 검색조건 파트너명
    if (searchForm.getAdminNm() != null) {
      booleanBuilder.and(qPartners.name.containsIgnoreCase(searchForm.getAdminNm()));
    }

    // 검색조건 전화번호
    if (searchForm.getPhone() != null) {
      booleanBuilder.and(qPartners.phone.containsIgnoreCase(searchForm.getPhone()));
    }

    // 검색조건 업체상태
    if (searchForm.getPartnerStatus() != null) {
      booleanBuilder.and(qPartners.status.in(searchForm.getPartnerStatus()));
    }

    // 검색조건 업체 지역
    if (searchForm.getAddress() != null) {
      booleanBuilder.and(qPartners.company.address.containsIgnoreCase(searchForm.getAddress()));
    }

    // 회사 아이디
    if (searchForm.getCompanyId() != null && searchForm.getCompanyId() > 0) {
      booleanBuilder.and(qPartners.company.id.eq(searchForm.getCompanyId()));
    }

    // 가입일 검색
    if (searchForm.getFromCreateDate() != null || searchForm.getToCreateDate() != null) {

      if (searchForm.getFromCreateDate() != null && searchForm.getToCreateDate() != null) {
        booleanBuilder.and(qPartners.createDate
            .between(searchForm.getFromCreateDate(), searchForm.getToCreateDate()));

      } else if (searchForm.getFromCreateDate() != null) {
        booleanBuilder.and(qPartners.createDate.goe(searchForm.getFromCreateDate()));

      } else if (searchForm.getToCreateDate() != null) {
        booleanBuilder.and(qPartners.createDate.loe(searchForm.getToCreateDate()));
      }
    }

    Page<PartnersListDto> page = this.partnersRepository.findAllPartnersList(booleanBuilder, pageable);

    var pagedResources = assembler.toResource(page, e -> new PartnersDtoListResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-partnerss-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(PartnersController.class).withRel("create-partners"));
    }
    return ResponseEntity.ok(pagedResources);
  }

  @GetMapping("/{id}")
  public ResponseEntity getPartners(@PathVariable Integer id,
                                    @CurrentUser Admin currentUser) {

    Optional<Partners> partnersOptional = this.partnersRepository.findById(id);

    // 업체 정보 체크
    if (partnersOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Partners partners = partnersOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    PartnersResource partnersResource = new PartnersResource(partners);
    partnersResource.add(new Link("/docs/index.html#resources-partners-get").withRel("profile"));

    return ResponseEntity.ok(partnersResource);
  }

  @GetMapping("/adminId/{id}")
  public ResponseEntity getPartnersAdminId(@PathVariable String id,
                                           @CurrentUser Admin currentUser) {

    Optional<Partners> partnersOptional = this.partnersRepository.findByAdminId(id);

    // 업체 정보 체크
    if (partnersOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Partners partners = partnersOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    PartnersResource partnersResource = new PartnersResource(partners);
    partnersResource.add(new Link("/docs/index.html#resources-partners-get").withRel("profile"));

    return ResponseEntity.ok(partnersResource);
  }




  @PutMapping("/{id}")
  public ResponseEntity putPartners(@PathVariable Integer id,
                                    @RequestBody @Valid Partners partners,
                                    Errors errors,
                                    @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    PartnersDto partnersDto = modelMapper.map(partners, PartnersDto.class);

    // 논리적 오류 (제약조건) 체크
    this.partnersValidator.validate(partnersDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Partners> partnersOptional = this.partnersRepository.findById(id);

    if (partnersOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Partners existPartners = partnersOptional.get();

    if ( currentUser != null ) {
      partners.setCreateBy(currentUser.getAdminNm());
      partners.setUpdateBy(currentUser.getAdminNm());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Partners savePartners = this.partnersRepository.save(partners);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    PartnersResource partnersResource = new PartnersResource(savePartners);
    partnersResource.add(new Link("/docs/index.html#resources-partners-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(partnersResource);
  }

  @PutMapping("/name/{id}")
  public ResponseEntity putPartnersName(@PathVariable Integer id,
                                        @RequestBody @Valid Partners partners,
                                        Errors errors,
                                        @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    PartnersDto partnersDto = modelMapper.map(partners, PartnersDto.class);

    // 논리적 오류 (제약조건) 체크
    this.partnersValidator.validate(partnersDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Partners> partnersOptional = this.partnersRepository.findByName(partners.getName());

    if (partnersOptional.isEmpty()) {

      // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
      // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
      this.partnersService.updateName(id, partners.getName());

      return ResponseEntity.ok().build();
    }

    // 정상적 처리
    return ResponseEntity.badRequest().build();
  }

//  @GetMapping("/excel")
//  public ModelAndView getExcelxls(SearchForm searchForm,
//                                  @AuthenticationPrincipal AdminAdapter user,
//                                  @CurrentUser Admin currentUser) {
//
//    List<PartnersListExcel> partnersListExcels = partnersService.getPartnersListExcel(searchForm);
//
//    ArrayList body = new ArrayList();
//    List bodes = new ArrayList();
//
//    for (PartnersListExcel partnersListExcel : partnersListExcels) {
//
//      body.add(Arrays.asList(
//          partnersListExcel.getId(),
//          partnersListExcel.getAdminId(),
//          partnersListExcel.getName(),
//          partnersListExcel.getManager(),
//          partnersListExcel.getPhone(),
//          partnersListExcel.getAddress(),
//          partnersListExcel.getCreateDate(),
//          partnersListExcel.getStatus(),
//          partnersListExcel.getPartnersMoveService(),
//          partnersListExcel.getRecommendCount(),
//          partnersListExcel.getPoint(),
//          partnersListExcel.getPointUpdateDate()));
//    }
//
//    return new ModelAndView("excelXlsView", initExcelData(body));
//  }

  private Map<String, Object> initExcelData(List body) {
    Map<String, Object> map = new HashMap<>();
    map.put(ExcelConstant.FILE_NAME, "partners");
    map.put(ExcelConstant.HEAD, Arrays
        .asList("NO", "ID", "업체명", "담당자", "연락처", "지역", "가입일자", "영업상태", "이사종류", "고객추천수", "포인트잔액",
            "최종포인트차감일"));
    map.put(ExcelConstant.BODY, body);

    return map;
  }


  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

}
