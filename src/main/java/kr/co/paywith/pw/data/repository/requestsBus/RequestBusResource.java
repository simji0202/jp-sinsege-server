package kr.co.paywith.pw.data.repository.requestsBus;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RequestBusResource extends Resource<RequestBus> {
	public RequestBusResource(RequestBus requestBus, Link... links) {
		super(requestBus, links);
		add(linkTo(RequestBusController.class).slash(requestBus.getId()).withSelfRel());

	}
}
