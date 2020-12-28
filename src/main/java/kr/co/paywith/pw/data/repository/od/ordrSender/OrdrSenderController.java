package kr.co.paywith.pw.data.repository.od.ordrSender;

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
@RequestMapping(value = "/api/ordrSender")
@Api(value = "OrdrSenderController", description = "쿠폰 API", basePath = "/api/ordrSender")
public class OrdrSenderController extends CommonController {

	 @Autowired
	 OrdrSenderRepository ordrSenderRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 OrdrSenderValidator ordrSenderValidator;

	 @Autowired
	 OrdrSenderService ordrSenderService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createOrdrSender(
				@RequestBody @Valid OrdrSenderDto ordrSenderDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  ordrSenderValidator.validate(ordrSenderDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  OrdrSender ordrSender = modelMapper.map(ordrSenderDto, OrdrSender.class);

		  // 레코드 등록
		  OrdrSender newOrdrSender = ordrSenderService.create(ordrSender);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(OrdrSenderController.class).slash(newOrdrSender.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrSenderResource ordrSenderResource = new OrdrSenderResource(newOrdrSender);
		  ordrSenderResource.add(linkTo(OrdrSenderController.class).withRel("query-ordrSender"));
		  ordrSenderResource.add(selfLinkBuilder.withRel("update-ordrSender"));
		  ordrSenderResource.add(new Link("/docs/index.html#resources-ordrSender-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(ordrSenderResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getOrdrSenders(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<OrdrSender> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QOrdrSender qOrdrSender = QOrdrSender.ordrSender;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qOrdrSender.id.eq(searchForm.getId()));
		  }


		  Page<OrdrSender> page = this.ordrSenderRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new OrdrSenderResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-ordrSenders-list").withRel("profile"));
		  pagedResources.add(linkTo(OrdrSenderController.class).withRel("create-ordrSender"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getOrdrSender(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<OrdrSender> ordrSenderOptional = this.ordrSenderRepository.findById(id);

		  // 고객 정보 체크
		  if (ordrSenderOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  OrdrSender ordrSender = ordrSenderOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrSenderResource ordrSenderResource = new OrdrSenderResource(ordrSender);
		  ordrSenderResource.add(new Link("/docs/index.html#resources-ordrSender-get").withRel("profile"));

		  return ResponseEntity.ok(ordrSenderResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putOrdrSender(@PathVariable Integer id,
											  @RequestBody @Valid OrdrSenderUpdateDto ordrSenderUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.ordrSenderValidator.validate(ordrSenderUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<OrdrSender> ordrSenderOptional = this.ordrSenderRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (ordrSenderOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  OrdrSender existOrdrSender = ordrSenderOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  OrdrSender saveOrdrSender = this.ordrSenderService.update(ordrSenderUpdateDto, existOrdrSender);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  OrdrSenderResource ordrSenderResource = new OrdrSenderResource(saveOrdrSender);
		  ordrSenderResource.add(new Link("/docs/index.html#resources-ordrSender-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(ordrSenderResource);
	 }
}
