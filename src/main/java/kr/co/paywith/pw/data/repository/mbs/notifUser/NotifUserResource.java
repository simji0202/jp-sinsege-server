package kr.co.paywith.pw.data.repository.mbs.notifUser;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class NotifUserResource extends Resource<NotifUser> {
	public NotifUserResource(NotifUser notifUser, Link... links) {
		super(notifUser, links);
		add(linkTo(NotifUserController.class).slash(notifUser.getId()).withSelfRel());

	}
}
