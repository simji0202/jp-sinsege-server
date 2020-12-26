package kr.co.paywith.pw.data.repository.od.ordrComment;

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
@RequestMapping(value = "/api/ordrComment")
@Api(value = "OrdrCommentController", description = "쿠폰 API", basePath = "/api/ordrComment")
public class OrdrCommentController extends CommonController {

	 @Autowired
	 OrdrCommentRepository ordrCommentRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrCommentValidator ordrCommentValidator;

	 @Autowired
	 OrdrCommentService ordrCommentService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrComment(
				@RequestBody @Valid OrdrCommentDto ordrCommentDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrCommentValidator.validate(ordrCommentDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrComment ordrComment = modelMapper.map(ordrCommentDto, OrdrComment.class);

		  // 레코드 등록
		  OrdrComment newOrdrComment = ordrCommentService.create(ordrComment);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrCommentController.class).slash(newOrdrComment.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCommentResource ordrCommentResource = new OrdrCommentResource(newOrdrComment);
		  ordrCommentResource.add(linkTo(OrdrCommentController.class).withRel("query-ordrComment"));
		  ordrCommentResource.add(selfLinkBuilder.withRel("update-ordrComment"));
		  ordrCommentResource.add(new Link("/docs/index.html#resources-ordrComment-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrCommentResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrComments(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrComment> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrComment qOrdrComment = QOrdrComment.ordrComment;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrComment.id.eq(searchForm.getId()));
		  }


		  Page<OrdrComment> page = this.ordrCommentRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrCommentResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrComments-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrCommentController.class).withRel("create-ordrComment"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrComment(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrComment> ordrCommentOptional = this.ordrCommentRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrCommentOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrComment ordrComment = ordrCommentOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCommentResource ordrCommentResource = new OrdrCommentResource(ordrComment);
		  ordrCommentResource.add(new Link("/docs/index.html#resources-ordrComment-get").withRel("profile"));

		  return ResponseEntity.ok(ordrCommentResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrComment(@PathVariable Integer id,
											  @RequestBody @Valid OrdrCommentUpdateDto ordrCommentUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrCommentValidator.validate(ordrCommentUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrComment> ordrCommentOptional = this.ordrCommentRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrCommentOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrComment existOrdrComment = ordrCommentOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrComment saveOrdrComment = this.ordrCommentService.update(ordrCommentUpdateDto, existOrdrComment);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCommentResource ordrCommentResource = new OrdrCommentResource(saveOrdrComment);
		  ordrCommentResource.add(new Link("/docs/index.html#resources-ordrComment-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrCommentResource);
	 }
}
