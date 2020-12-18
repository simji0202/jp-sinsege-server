package kr.co.paywith.pw.data.repository.user.userInfo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserInfoResource extends Resource<UserInfo> {

    public UserInfoResource(UserInfo userInfo, Link... links) {
        super(userInfo, links);
        add(linkTo(UserInfoController.class).slash(userInfo.getId()).withSelfRel());

    }
}
