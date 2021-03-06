package kr.co.paywith.pw.data.repository.account;

import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/account")
@Api(value = "AccountController", description = "로그인 API", basePath = "/api/account")
public class AccountController extends CommonController {


    /**
     *  로그인 중복 아이디 체크
     */
    @GetMapping
    public ResponseEntity getMrhstTrmnls(SearchForm searchForm,
                                         @PathVariable Integer id,
                                         @CurrentUser Account currentUser) {

        // 로그인 중복 체크 구현 예정

        return null;
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }



}



