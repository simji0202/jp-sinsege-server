package kr.co.paywith.pw.data.repository.agents;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminAdapter;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.partners.PartnersResource;
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
@RequestMapping(value = "/api/agents", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "AgentsController", description = "에이젼트 화면 API")
public class AgentsController {

  @Autowired
  private AgentsRepository agentsRepository;

  @Autowired
  private AgentsValidator agentsValidator;

  @Autowired
  private ModelMapper modelMapper;


  @Autowired
  private AgentsService agentsService;

	@PostMapping
	private ResponseEntity insertAgents(@RequestBody @Valid AgentsDto agentsDto,
										Errors errors,
										@CurrentUser Admin currentUser) {
		// 입력 값 체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		agentsValidator.validate(agentsDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Agents newAgents = this.agentsService.createAgents(agentsDto, currentUser);

		ControllerLinkBuilder selfLinkBuilder = linkTo(AgentsController.class).slash(newAgents.getId());
		URI createdUri = selfLinkBuilder.toUri();

		AgentsResource agentsResource = new AgentsResource(newAgents);
		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		agentsResource.add(linkTo(AgentsController.class).withRel("query-agents"));
		agentsResource.add(selfLinkBuilder.withRel("update-agents"));
		agentsResource.add(new Link("/docs/index.html#resources-agents-create").withRel("profile"));

		return ResponseEntity.created(createdUri).body(agentsResource);

	}


  @GetMapping
  public ResponseEntity getAgentss(SearchForm searchForm,
                                   Pageable pageable,
                                   PagedResourcesAssembler<Agents> assembler
      , @AuthenticationPrincipal AdminAdapter user
      , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QAgents qAgents = QAgents.agents;

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 에이젼트ID
    if (searchForm.getAdminId() != null) {
      booleanBuilder.and(qAgents.adminId.eq(searchForm.getAdminId()));
    }

    Page<Agents> page = this.agentsRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new AgentsResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-agentss-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(AgentsController.class).withRel("create-agents"));
    }
    return ResponseEntity.ok(pagedResources);
  }


  @GetMapping("/{id}")
  public ResponseEntity getAgents(@PathVariable Integer id,
                                  @CurrentUser Admin currentUser) {

    Optional<Agents> agentsOptional = this.agentsRepository.findById(id);

    // 에이전트 정보 체크
    if (agentsOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Agents agents = agentsOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    AgentsResource agentsResource = new AgentsResource(agents);
    agentsResource.add(new Link("/docs/index.html#resources-agents-get").withRel("profile"));

    return ResponseEntity.ok(agentsResource);
  }


  @PutMapping("/{id}")
  public ResponseEntity putAgents(@PathVariable Integer id,
                                  @RequestBody @Valid AgentsUpdateDto agentsUpdateDto,
                                  Errors errors,
                                  @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.agentsValidator.validate(agentsUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Agents> agentsOptional = this.agentsRepository.findById(id);

    if (agentsOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Agents existAgents = agentsOptional.get();
//			if (!existingEvent.getManager().equals(currentUser)) {
//				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//			}

    this.modelMapper.map(agentsUpdateDto, existAgents);

    if (currentUser != null) {
      // 변경자 정보 저장
      existAgents.setUpdateBy(currentUser.getAdminNm());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Agents saveAgents = this.agentsRepository.save(existAgents);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    AgentsResource agentsResource = new AgentsResource(saveAgents);
    agentsResource.add(new Link("/docs/index.html#resources-agents-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(agentsResource);

  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

  @GetMapping("/adminId/{id}")
  public ResponseEntity getAgentsAdminId(@PathVariable String id,
                                           @CurrentUser Admin currentUser) {

    Optional<Agents> agentsOptional = this.agentsRepository.findByAdminId(id);

    // 업체 정보 체크
    if (agentsOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Agents agents = agentsOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    AgentsResource agentsResource = new AgentsResource(agents);
    agentsResource.add(new Link("/docs/index.html#resources-agents-get").withRel("profile"));

    return ResponseEntity.ok(agentsResource);
  }
}
