package kr.co.paywith.pw.data.repository.user.userDel;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserDelResource extends Resource<UserDel> {

    public UserDelResource(UserDel userDel, Link... links) {
        super(userDel, links);
        add(linkTo(UserDelController.class).slash(userDel.getId()).withSelfRel());

    }
}
