package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class NotifMrhstResource extends Resource<NotifMrhst> {
	public NotifMrhstResource(NotifMrhst notifMrhst, Link... links) {
		super(notifMrhst, links);
		add(linkTo(NotifMrhstController.class).slash(notifMrhst.getId()).withSelfRel());

	}
}
