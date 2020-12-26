package kr.co.paywith.pw.data.repository.od.userMrhst;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserMrhstResource extends Resource<UserMrhst> {
	public UserMrhstResource(UserMrhst userMrhst, Link... links) {
		super(userMrhst, links);
		add(linkTo(UserMrhstController.class).slash(userMrhst.getId()).withSelfRel());

	}
}
