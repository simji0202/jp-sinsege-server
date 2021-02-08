package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;import org.modelmapper.ModelMapper;
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
@RequestMapping(value = "/api/delngOrdr")
@Api(value = "DelngOrdrController", description = "쿠폰 API", basePath = "/api/delngOrdr")
public class DelngOrdrController extends CommonController {

	 @Autowired
	 DelngOrdrRepository delngOrdrRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 DelngOrdrValidator delngOrdrValidator;

	 @Autowired
	 DelngOrdrService delngOrdrService;

//	 /**
//	  * 정보 등록
//	  */
//	 @PostMapping
//	 public ResponseEntity createDelngOrdr(
//				@RequestBody @Valid DelngOrdrDto delngOrdrDto,
//				Errors errors,
//				@CurrentUser Account currentUser) {
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값 체크
//		  delngOrdrValidator.validate(delngOrdrDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//
//		  // 입력값을 브랜드 객채에 대입
//		  DelngOrdr delngOrdr = modelMapper.map(delngOrdrDto, DelngOrdr.class);
//
//		  // 레코드 등록
//		  DelngOrdr newDelngOrdr = delngOrdrService.create(delngOrdr);
//
//		  ControllerLinkBuilder selfLinkBuilder = linkTo(DelngOrdrController.class).slash(newDelngOrdr.getId());
//
//		  URI createdUri = selfLinkBuilder.toUri();
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  DelngOrdrResource delngOrdrResource = new DelngOrdrResource(newDelngOrdr);
//		  delngOrdrResource.add(linkTo(DelngOrdrController.class).withRel("query-delngOrdr"));
//		  delngOrdrResource.add(selfLinkBuilder.withRel("update-delngOrdr"));
//		  delngOrdrResource.add(new Link("/docs/index.html#resources-delngOrdr-create").withRel("profile"));
//
//		  return ResponseEntity.created(createdUri).body(delngOrdrResource);
//	 }
//
//
//	 /**
//	  * 정보취득 (조건별 page )
//	  */
//	 @GetMapping
//	 public ResponseEntity getDelngOrdrs(SearchForm searchForm,
//												Pageable pageable,
//												PagedResourcesAssembler<DelngOrdr> assembler
//				, @CurrentUser Account currentUser) {
//
//		  // 인증상태의 유저 정보 확인
////		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////		  User princpal = (User) authentication.getPrincipal();
//
//		  QDelngOrdr qDelngOrdr = QDelngOrdr.delngOrdr;
//
//		  //
//		  BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//		  // 검색조건 아이디(키)
//		  if (searchForm.getId() != null) {
//				booleanBuilder.and(qDelngOrdr.id.eq(searchForm.getId()));
//		  }
//
//
//		  Page<DelngOrdr> page = this.delngOrdrRepository.findAll(booleanBuilder, pageable);
//		  var pagedResources = assembler.toResource(page, e -> new DelngOrdrResource(e));
//		  pagedResources.add(new Link("/docs/index.html#resources-delngOrdrs-list").withRel("profile"));
//		  pagedResources.add(linkTo(DelngOrdrController.class).withRel("create-delngOrdr"));
//		  return ResponseEntity.ok(pagedResources);
//	 }
//
//	 private ResponseEntity badRequest(Errors errors) {
//		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
//	 }
//
//
//	 /**
//	  * 정보취득 (1건 )
//	  */
//	 @GetMapping("/{id}")
//	 public ResponseEntity getDelngOrdr(@PathVariable Integer id,
//											  @CurrentUser Account currentUser) {
//
//		  Optional<DelngOrdr> delngOrdrOptional = this.delngOrdrRepository.findById(id);
//
//		  // 고객 정보 체크
//		  if (delngOrdrOptional.isEmpty()) {
//				return ResponseEntity.notFound().build();
//		  }
//
//		  DelngOrdr delngOrdr = delngOrdrOptional.get();
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  DelngOrdrResource delngOrdrResource = new DelngOrdrResource(delngOrdr);
//		  delngOrdrResource.add(new Link("/docs/index.html#resources-delngOrdr-get").withRel("profile"));
//
//		  return ResponseEntity.ok(delngOrdrResource);
//	 }
//
//
//	 /**
//	  * 정보 변경
//	  */
//	 @PutMapping("/{id}")
//	 public ResponseEntity putDelngOrdr(@PathVariable Integer id,
//											  @RequestBody @Valid DelngOrdrUpdateDto delngOrdrUpdateDto,
//											  Errors errors,
//											  @CurrentUser Account currentUser) {
//		  // 입력체크
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 논리적 오류 (제약조건) 체크
//		  this.delngOrdrValidator.validate(delngOrdrUpdateDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 기존 테이블에서 관련 정보 취득
//		  Optional<DelngOrdr> delngOrdrOptional = this.delngOrdrRepository.findById(id);
//
//		  // 기존 정보 유무 체크
//		  if (delngOrdrOptional.isEmpty()) {
//				// 404 Error return
//				return ResponseEntity.notFound().build();
//		  }
//
//		  // 기존 정보 취득
//		  DelngOrdr existDelngOrdr = delngOrdrOptional.get();
//
//		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//		  DelngOrdr saveDelngOrdr = this.delngOrdrService.update(delngOrdrUpdateDto, existDelngOrdr);
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  DelngOrdrResource delngOrdrResource = new DelngOrdrResource(saveDelngOrdr);
//		  delngOrdrResource.add(new Link("/docs/index.html#resources-delngOrdr-update").withRel("profile"));
//
//		  // 정상적 처리
//		  return ResponseEntity.ok(delngOrdrResource);
//	 }
}
