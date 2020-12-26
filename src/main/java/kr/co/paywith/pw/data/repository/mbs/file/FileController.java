package kr.co.paywith.pw.data.repository.mbs.file;

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
@RequestMapping(value = "/api/file")
@Api(value = "FileController", description = "쿠폰 API", basePath = "/api/file")
public class FileController extends CommonController {

	 @Autowired
	 FileRepository fileRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 FileValidator fileValidator;

	 @Autowired
	 FileService fileService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createFile(
				@RequestBody @Valid FileDto fileDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  fileValidator.validate(fileDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  File file = modelMapper.map(fileDto, File.class);

		  // 레코드 등록
		  File newFile = fileService.create(file);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(FileController.class).slash(newFile.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  FileResource fileResource = new FileResource(newFile);
		  fileResource.add(linkTo(FileController.class).withRel("query-file"));
		  fileResource.add(selfLinkBuilder.withRel("update-file"));
		  fileResource.add(new Link("/docs/index.html#resources-file-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(fileResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getFiles(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<File> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QFile qFile = QFile.file;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qFile.id.eq(searchForm.getId()));
		  }


		  Page<File> page = this.fileRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new FileResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-files-list").withRel("profile"));
		  pagedResources.add(linkTo(FileController.class).withRel("create-file"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getFile(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<File> fileOptional = this.fileRepository.findById(id);

		  // 고객 정보 체크
		  if (fileOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  File file = fileOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  FileResource fileResource = new FileResource(file);
		  fileResource.add(new Link("/docs/index.html#resources-file-get").withRel("profile"));

		  return ResponseEntity.ok(fileResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putFile(@PathVariable Integer id,
											  @RequestBody @Valid FileUpdateDto fileUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.fileValidator.validate(fileUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<File> fileOptional = this.fileRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (fileOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  File existFile = fileOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  File saveFile = this.fileService.update(fileUpdateDto, existFile);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  FileResource fileResource = new FileResource(saveFile);
		  fileResource.add(new Link("/docs/index.html#resources-file-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(fileResource);
	 }
}
