package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MrhstSeatResource extends Resource<MrhstSeat> {
	public MrhstSeatResource(MrhstSeat mrhstSeat, Link... links) {
		super(mrhstSeat, links);
		add(linkTo(MrhstSeatController.class).slash(mrhstSeat.getId()).withSelfRel());

	}
}
