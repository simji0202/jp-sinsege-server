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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping(value = "/api/company", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "CompanyController", description = "여행사 업체 관리자 API", basePath = "/api/company")
public class CompanyController {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private ModelMapper modelMapper;


  @GetMapping
  public ResponseEntity getCompanys(SearchForm searchForm,
                                    Pageable pageable,
                                    PagedResourcesAssembler<Company> assembler
          , @AuthenticationPrincipal AdminAdapter user
          , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QCompany qCompany = QCompany.company;

    BooleanBuilder booleanBuilder = new BooleanBuilder();


    // 회사 아이디
    if (searchForm.getCompanyId() != null && searchForm.getCompanyId() > 0) {
      booleanBuilder.and(qCompany.id.eq(searchForm.getCompanyId()));
    }

    // 검색조건 업체명
    if (searchForm.getAdminNm() != null) {
      booleanBuilder.and(qCompany.companyNm.containsIgnoreCase(searchForm.getAdminNm()));
    }


    Page<Company> page = this.companyRepository.findAll(booleanBuilder, pageable);

    var pagedResources = assembler.toResource(page, e -> new CompanyResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-companys-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(CompanyController.class).withRel("create-company"));
    }
    return ResponseEntity.ok(pagedResources);
  }

  @GetMapping("/{id}")
  public ResponseEntity getCompany(@PathVariable Integer id,
                                   @CurrentUser Admin currentUser) {

    Optional<Company> companyOptional = this.companyRepository.findById(id);

    // 업체 정보 체크
    if (companyOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Company company = companyOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    CompanyResource companyResource = new CompanyResource(company);
    companyResource.add(new Link("/docs/index.html#resources-company-get").withRel("profile"));

    return ResponseEntity.ok(companyResource);
  }




  @PutMapping("/{id}")
  public ResponseEntity putPartners(@PathVariable Integer id,
                                    @RequestBody @Valid Company company,
                                    Errors errors,
                                    @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Company> companyOptional = this.companyRepository.findById(id);

    if (companyOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Company saveCompany = this.companyRepository.save(company);


    // 정상적 처리
    return ResponseEntity.ok(saveCompany);
  }


  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

}