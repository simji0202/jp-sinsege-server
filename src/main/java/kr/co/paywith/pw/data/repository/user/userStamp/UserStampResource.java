package kr.co.paywith.pw.data.repository.user.userStamp;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserStampResource extends Resource<UserStamp> {
	public UserStampResource(UserStamp userStamp, Link... links) {
		super(userStamp, links);
		add(linkTo(UserStampController.class).slash(userStamp.getId()).withSelfRel());

	}
}
