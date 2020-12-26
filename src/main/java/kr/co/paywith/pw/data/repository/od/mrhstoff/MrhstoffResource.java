package kr.co.paywith.pw.data.repository.od.mrhstoff;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MrhstoffResource extends Resource<Mrhstoff> {
	public MrhstoffResource(Mrhstoff mrhstoff, Link... links) {
		super(mrhstoff, links);
		add(linkTo(MrhstoffController.class).slash(mrhstoff.getId()).withSelfRel());

	}
}
