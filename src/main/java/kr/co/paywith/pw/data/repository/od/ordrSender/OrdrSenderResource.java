package kr.co.paywith.pw.data.repository.od.ordrSender;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrSenderResource extends Resource<OrdrSender> {
	public OrdrSenderResource(OrdrSender ordrSender, Link... links) {
		super(ordrSender, links);
		add(linkTo(OrdrSenderController.class).slash(ordrSender.getId()).withSelfRel());

	}
}
