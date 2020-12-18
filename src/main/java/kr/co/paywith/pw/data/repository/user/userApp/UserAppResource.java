package kr.co.paywith.pw.data.repository.user.userApp;

import kr.co.paywith.pw.data.repository.mbs.brand.BrandController;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserAppResource extends Resource<UserApp> {

    public UserAppResource(UserApp userApp, Link... links) {
        super(userApp, links);
        add(linkTo(UserAppController.class).slash(userApp.getId()).withSelfRel());

    }
}
