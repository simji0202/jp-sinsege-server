package kr.co.paywith.pw.data.repository.mbs.notif;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class NotifResource extends Resource<Notif> {
	public NotifResource(Notif notif, Link... links) {
		super(notif, links);
		add(linkTo(NotifController.class).slash(notif.getId()).withSelfRel());

	}
}
