package kr.co.paywith.pw.data.repository.mbs.stamp;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class StampResource extends Resource<Stamp> {
	public StampResource(Stamp stamp, Link... links) {
		super(stamp, links);
		add(linkTo(StampController.class).slash(stamp.getId()).withSelfRel());

	}
}
