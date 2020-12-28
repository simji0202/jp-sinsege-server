package kr.co.paywith.pw.data.repository.od.ordrHist;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrHistResource extends Resource<OrdrHist> {
	public OrdrHistResource(OrdrHist ordrHist, Link... links) {
		super(ordrHist, links);
		add(linkTo(OrdrHistController.class).slash(ordrHist.getId()).withSelfRel());

	}
}
