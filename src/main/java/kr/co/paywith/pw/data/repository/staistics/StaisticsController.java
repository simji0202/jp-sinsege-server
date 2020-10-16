package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminAdapter;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.partners.PartnersRepository;
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

import java.text.ParseException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/statistics", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class StaisticsController {

  @Autowired
  private PartnersRepository partnersRepository;

  @Autowired
  private StatisticsValidator statisticsValidator;

  @Autowired
  private StatisticsService statisticsService;



  /**
   * 메인 프론트 화면 통계 정보 수집
   *
   * @param searchForm
   * @param errors
   * @param currentUser
   * @return
   */
  @GetMapping("/front")
  public ResponseEntity getFrontStatistics(SearchForm searchForm,
                                           Pageable pageable,
                                           PagedResourcesAssembler<Admin> assembler,
                                            @AuthenticationPrincipal AdminAdapter user,
                                           Errors errors,
                                           @CurrentUser Admin currentUser) {

    System.out.println("### Statistics start ###");

    return ResponseEntity.ok(this.statisticsService.getFrontStatics());
  }

  /**
   * @param errors
   * @return
   */
  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

}
