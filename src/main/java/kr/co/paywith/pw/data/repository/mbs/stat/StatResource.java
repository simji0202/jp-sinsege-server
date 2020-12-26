package kr.co.paywith.pw.data.repository.mbs.stat;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class StatResource extends Resource<Stat> {
	public StatResource(Stat stat, Link... links) {
		super(stat, links);
		add(linkTo(StatController.class).slash(stat.getId()).withSelfRel());

	}
}
