package kr.co.paywith.pw.data.repository.mbs.chrg;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ChrgResource extends Resource<Chrg> {
	public ChrgResource(Chrg chrg, Link... links) {
		super(chrg, links);
		add(linkTo(ChrgController.class).slash(chrg.getId()).withSelfRel());

	}
}
