package kr.co.paywith.pw.data.repository.user.userInfo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/userInfo")
@Api(value = "UserInfoController", description = "일반유저  API", basePath = "/api/userInfo")
public class UserInfoController extends CommonController {

  @Autowired
  UserInfoRepository userInfoRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  UserInfoValidator userInfoValidator;

  @Autowired
  UserInfoService userInfoService;

  @PostMapping
  public ResponseEntity createUserInfo(
      @RequestBody @Valid UserInfoDto userInfoDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    this.userInfoValidator.validate(userInfoDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);


    //  등록자 정보 설정
    if (currentUser != null) {
      userInfo.setUpdateBy(currentUser.getAccountId());
      userInfo.setCreateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    UserInfo newUserInfo = userInfoService.save(userInfo);

    ControllerLinkBuilder selfLinkBuilder = linkTo(UserInfoController.class)
        .slash(newUserInfo.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    UserInfoResource userInfoResource = new UserInfoResource(newUserInfo);
    userInfoResource.add(linkTo(UserInfoController.class).withRel("query-userInfo"));
    userInfoResource.add(selfLinkBuilder.withRel("update-userInfo"));
    userInfoResource.add(new Link("/docs/index.html#resources-userInfo-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(userInfoResource);
  }


  @GetMapping
  public ResponseEntity getUserInfos(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<UserInfo> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

    QUserInfo qUserInfo = QUserInfo.userInfo;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 탈퇴해서 개인정보 삭제한 회원은 표시하지 않음
    booleanBuilder.and(qUserInfo.userId.isNotNull());

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qUserInfo.id.eq(searchForm.getId()));
    }

    Page<UserInfo> page = this.userInfoRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new UserInfoResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-userInfos-list").withRel("profile"));
    pagedResources.add(linkTo(UserInfoController.class).withRel("create-userInfo"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   *
   */
  @GetMapping("/{id}")
  public ResponseEntity getUserInfo(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<UserInfo> userInfoOptional = this.userInfoRepository.findById(id);

    // 고객 정보 체크
    if (userInfoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UserInfo userInfo = userInfoOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    UserInfoResource userInfoResource = new UserInfoResource(userInfo);
    userInfoResource.add(new Link("/docs/index.html#resources-userInfo-get").withRel("profile"));

    return ResponseEntity.ok(userInfoResource);
  }


  /**
   *
   */
  @PutMapping("/{id}")
  public ResponseEntity putUserInfo(@PathVariable Integer id,
      @RequestBody @Valid UserInfoUpdateDto userInfoUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    userInfoUpdateDto.setId(id);

    // 논리적 오류 (제약조건) 체크
    this.userInfoValidator.validate(userInfoUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 기존 테이블에서 관련 정보 취득
    Optional<UserInfo> userInfoOptional = this.userInfoRepository.findById(id);

    // 기존 정보 유무 체크
    if (userInfoOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 기존 정보 취득
    UserInfo existUserInfo = userInfoOptional.get();

    if (currentUser != null) {
      // 변경자 정보 저장
      //           existUserInfo.setUpdateBy(currentUser.getAccountId());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    UserInfo saveUserInfo = this.userInfoService.update(userInfoUpdateDto, existUserInfo);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    UserInfoResource userInfoResource = new UserInfoResource(saveUserInfo);
    userInfoResource.add(new Link("/docs/index.html#resources-userInfo-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(userInfoResource);
  }


  /**
   * 암호 변경
   */
  @PutMapping("/updatePw/{id}")
  public ResponseEntity putUserInfoChangeUserInfoPw(@PathVariable Integer id,
      @RequestBody @Valid UserInfoPwUpdateDto userInfoPwUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.userInfoValidator.validate(userInfoPwUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<UserInfo> userInfoOptional = this.userInfoRepository.findById(id);

    if (userInfoOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    UserInfo existUserInfo = userInfoOptional.get();

    // 패스워드 설정
    existUserInfo.setUserPw(userInfoPwUpdateDto.getUserPw());

    //  변경자 정보 설정
    if (currentUser != null) {
      existUserInfo.setUpdateBy(currentUser.getAccountId());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    UserInfo saveUserInfo = this.userInfoService.updatePw(existUserInfo);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    UserInfoResource userInfoResource = new UserInfoResource(saveUserInfo);
    userInfoResource.add(new Link("/docs/index.html#resources-userInfo-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(userInfoResource);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity removeUserInfo(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<UserInfo> userInfoOptional = this.userInfoRepository.findById(id);

    if (userInfoOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // kms: TODO 상태 검증 필요

    // 탈퇴 가능한 지(본인 또는 관리자) 확인
    UserInfo userInfo = userInfoOptional.get();
    if (!currentUser.getUserInfo().getId().equals(id) ||
        false // TODO 관리자 권한일때도 탈퇴처리 가능해야 함
    ) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    userInfoService.outUser(userInfo);

    // 정상적 처리
    return ResponseEntity.ok().build();

  }

  @GetMapping("/checkExist")
  public ResponseEntity checkExist(UserInfoCheckExistDto userInfoCheckExistDto
  ) {

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QUserInfo qUserInfo = QUserInfo.userInfo;

    if (userInfoCheckExistDto.getUserId() != null) {
      booleanBuilder.and(qUserInfo.userId.eq(userInfoCheckExistDto.getUserId()));
    }
    if (userInfoCheckExistDto.getCertTypeCd() != null) {
      booleanBuilder.and(qUserInfo.certTypeCd.eq(userInfoCheckExistDto.getCertTypeCd()));
    }
    if (userInfoCheckExistDto.getCertKey() != null) {
      booleanBuilder.and(qUserInfo.certKey.eq(userInfoCheckExistDto.getCertKey()));
    }
    if (userInfoCheckExistDto.getEmailAddr() != null) {
      booleanBuilder.and(qUserInfo.emailAddr.eq(userInfoCheckExistDto.getEmailAddr()));
    }
    if (userInfoCheckExistDto.getMobileNum() != null) {
      booleanBuilder.and(qUserInfo.mobileNum.eq(
          userInfoCheckExistDto.getMobileNum().trim().replaceAll("-", "")));
    }

    List<Integer> idList = new ArrayList<>();
    for (UserInfo userInfo : userInfoRepository.findAll(booleanBuilder)) {
      idList.add(userInfo.getId());
    }
    // 정상적 처리
    return ResponseEntity.ok(Map.of("userInfoIdList", idList));

  }
}



