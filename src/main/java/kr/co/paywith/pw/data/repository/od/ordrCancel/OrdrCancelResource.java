package kr.co.paywith.pw.data.repository.od.ordrCancel;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrCancelResource extends Resource<OrdrCancel> {
	public OrdrCancelResource(OrdrCancel ordrCancel, Link... links) {
		super(ordrCancel, links);
		add(linkTo(OrdrCancelController.class).slash(ordrCancel.getId()).withSelfRel());

	}
}
