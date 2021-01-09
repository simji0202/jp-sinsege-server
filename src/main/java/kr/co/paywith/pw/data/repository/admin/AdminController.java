package kr.co.paywith.pw.data.repository.admin;


import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;

import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/admin", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "AdminController", description = "관리자 화면 API", basePath = "/api/admin")
public class AdminController {


  @Autowired
  AdminRepository adminRepository;


  @Autowired
  AdminService adminService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  AdminValidator adminValidator;

  @PostMapping
  public ResponseEntity createAdmin(@RequestBody @Valid AdminDto adminDto,
                                    Errors errors,
                                    @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    adminValidator.validate(adminDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Admin admin = modelMapper.map(adminDto, Admin.class);

    if (currentUser != null) {
      admin.setUpdateBy(currentUser.getAccountId());
      admin.setCreateBy(currentUser.getAccountId());
    }

    Admin newAdmin = this.adminService.create(admin);

    ControllerLinkBuilder selfLinkBuilder = linkTo(AdminController.class).slash(newAdmin.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    AdminResource adminResource = new AdminResource(newAdmin);
    adminResource.add(linkTo(AdminController.class).withRel("query-admin"));
    adminResource.add(selfLinkBuilder.withRel("update-admin"));
    adminResource.add(new Link("/docs/index.html#resources-admin-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(adminResource);

  }


  @GetMapping
  public ResponseEntity getAdmins(SearchForm searchForm,
                                  Pageable pageable,
                                  PagedResourcesAssembler<Admin> assembler
      , @CurrentUser Account currentUser) {


    // 검색 ID
    QAdmin admin = QAdmin.admin;

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 key
    if (searchForm.getId() != null && searchForm.getId() > 0) {
      booleanBuilder.and(admin.id.eq(searchForm.getId()));
    }

//    // 검색조건 타입
//    if (searchForm.getAdminType() != null ) {
//      booleanBuilder.and(admin.adminType.eq(searchForm.getAdminType()));
//    }

    // 검색조건 관리자, 업체, 에이전트 id"
    if (searchForm.getAdminId() != null ) {
      booleanBuilder.and(admin.adminId.containsIgnoreCase(searchForm.getAdminId()));
    }


    Page<Admin> page = this.adminRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new AdminResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-admins-list").withRel("profile"));

    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  @GetMapping("/{id}")
  public ResponseEntity getAdmin(@PathVariable Integer id,
                                 @CurrentUser Account currentUser) {

    Optional<Admin> adminOptional = this.adminRepository.findById(id);

    // 고객 정보 체크
    if (adminOptional.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    Admin admin = adminOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    AdminResource adminResource = new AdminResource(admin);
    adminResource.add(new Link("/docs/index.html#resources-admin-get").withRel("profile"));

    return ResponseEntity.ok(adminResource);
  }






  @PutMapping("/{id}")
  public ResponseEntity putAdmin(@PathVariable Integer id,
                                 @RequestBody @Valid AdminUpdateDto adminUpdateDto,
                                 Errors errors,
                                 @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.adminValidator.validate(adminUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Admin> adminOptional = this.adminRepository.findById(id);

    if (adminOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Admin existAdmin = adminOptional.get();
//            if (!existAdmin.().equals(currentUser)) {
//                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//            }

    this.modelMapper.map(adminUpdateDto, existAdmin);

    //  변경자 정보 설정
    if (currentUser != null) {
        existAdmin.setUpdateBy(currentUser.getAccountId());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Admin saveAdmin = this.adminRepository.save(existAdmin);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    AdminResource adminResource = new AdminResource(saveAdmin);
    adminResource.add(new Link("/docs/index.html#resources-admin-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(adminResource);

  }


  /**
   * 암호 변경
   */
    @PutMapping("/updatePw/{id}")
    public ResponseEntity putAdminChangeAdminPw(@PathVariable Integer id,
                                   @RequestBody @Valid AdminPwUpdateDto adminPwUpdateDto,
                                   Errors errors,
                                   @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 논리적 오류 (제약조건) 체크
        this.adminValidator.validate(adminPwUpdateDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            // 404 Error return
            return ResponseEntity.notFound().build();
        }

        Admin existAdmin = adminOptional.get();


        // 패스워드 설정
        existAdmin.setAdminPw(adminPwUpdateDto.getAdminPw());

        //  변경자 정보 설정
        if (currentUser != null) {
            existAdmin.setUpdateBy(currentUser.getAccountId());
        }

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Admin saveAdmin = this.adminService.updateAdminPw(existAdmin);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        AdminResource adminResource = new AdminResource(saveAdmin);
        adminResource.add(new Link("/docs/index.html#resources-admin-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(adminResource);
    }
}
