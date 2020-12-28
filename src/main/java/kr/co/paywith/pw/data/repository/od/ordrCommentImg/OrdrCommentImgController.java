package kr.co.paywith.pw.data.repository.od.ordrCommentImg;

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
@RequestMapping(value = "/api/ordrCommentImg")
@Api(value = "OrdrCommentImgController", description = "쿠폰 API", basePath = "/api/ordrCommentImg")
public class OrdrCommentImgController extends CommonController {

	 @Autowired
	 OrdrCommentImgRepository ordrCommentImgRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrCommentImgValidator ordrCommentImgValidator;

	 @Autowired
	 OrdrCommentImgService ordrCommentImgService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrCommentImg(
				@RequestBody @Valid OrdrCommentImgDto ordrCommentImgDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrCommentImgValidator.validate(ordrCommentImgDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrCommentImg ordrCommentImg = modelMapper.map(ordrCommentImgDto, OrdrCommentImg.class);

		  // 레코드 등록
		  OrdrCommentImg newOrdrCommentImg = ordrCommentImgService.create(ordrCommentImg);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrCommentImgController.class).slash(newOrdrCommentImg.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCommentImgResource ordrCommentImgResource = new OrdrCommentImgResource(newOrdrCommentImg);
		  ordrCommentImgResource.add(linkTo(OrdrCommentImgController.class).withRel("query-ordrCommentImg"));
		  ordrCommentImgResource.add(selfLinkBuilder.withRel("update-ordrCommentImg"));
		  ordrCommentImgResource.add(new Link("/docs/index.html#resources-ordrCommentImg-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrCommentImgResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrCommentImgs(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrCommentImg> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrCommentImg qOrdrCommentImg = QOrdrCommentImg.ordrCommentImg;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrCommentImg.id.eq(searchForm.getId()));
		  }


		  Page<OrdrCommentImg> page = this.ordrCommentImgRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrCommentImgResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrCommentImgs-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrCommentImgController.class).withRel("create-ordrCommentImg"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrCommentImg(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrCommentImg> ordrCommentImgOptional = this.ordrCommentImgRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrCommentImgOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrCommentImg ordrCommentImg = ordrCommentImgOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCommentImgResource ordrCommentImgResource = new OrdrCommentImgResource(ordrCommentImg);
		  ordrCommentImgResource.add(new Link("/docs/index.html#resources-ordrCommentImg-get").withRel("profile"));

		  return ResponseEntity.ok(ordrCommentImgResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrCommentImg(@PathVariable Integer id,
											  @RequestBody @Valid OrdrCommentImgUpdateDto ordrCommentImgUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrCommentImgValidator.validate(ordrCommentImgUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrCommentImg> ordrCommentImgOptional = this.ordrCommentImgRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrCommentImgOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrCommentImg existOrdrCommentImg = ordrCommentImgOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrCommentImg saveOrdrCommentImg = this.ordrCommentImgService.update(ordrCommentImgUpdateDto, existOrdrCommentImg);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrCommentImgResource ordrCommentImgResource = new OrdrCommentImgResource(saveOrdrCommentImg);
		  ordrCommentImgResource.add(new Link("/docs/index.html#resources-ordrCommentImg-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrCommentImgResource);
	 }
}
