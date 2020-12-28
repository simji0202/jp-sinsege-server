package kr.co.paywith.pw.data.repository.od.ordrDeliv;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrDelivResource extends Resource<OrdrDeliv> {
	public OrdrDelivResource(OrdrDeliv ordrDeliv, Link... links) {
		super(ordrDeliv, links);
		add(linkTo(OrdrDelivController.class).slash(ordrDeliv.getId()).withSelfRel());

	}
}
