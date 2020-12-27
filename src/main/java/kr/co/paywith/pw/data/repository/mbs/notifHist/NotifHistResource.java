package kr.co.paywith.pw.data.repository.mbs.notifHist;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class NotifHistResource extends Resource<NotifHist> {
	public NotifHistResource(NotifHist notifHist, Link... links) {
		super(notifHist, links);
		add(linkTo(NotifHistController.class).slash(notifHist.getId()).withSelfRel());

	}
}
