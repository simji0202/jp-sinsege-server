package kr.co.paywith.pw.data.repository.user.userCard;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserCardResource extends Resource<UserCard> {
	public UserCardResource(UserCard userCard, Link... links) {
		super(userCard, links);
		add(linkTo(UserCardController.class).slash(userCard.getId()).withSelfRel());

	}
}
