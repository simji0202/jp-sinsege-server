package kr.co.paywith.pw.data.repository.mbs.stampHist;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class StampHistResource extends Resource<StampHist> {
	public StampHistResource(StampHist stampHist, Link... links) {
		super(stampHist, links);
		add(linkTo(StampHistController.class).slash(stampHist.getId()).withSelfRel());

	}
}
