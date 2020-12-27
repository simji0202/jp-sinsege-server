package kr.co.paywith.pw.data.repository.mbs.gift;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class GiftResource extends Resource<Gift> {
	public GiftResource(Gift gift, Link... links) {
		super(gift, links);
		add(linkTo(GiftController.class).slash(gift.getId()).withSelfRel());

	}
}
