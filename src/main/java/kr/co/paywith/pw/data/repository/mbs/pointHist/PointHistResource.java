package kr.co.paywith.pw.data.repository.mbs.pointHist;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PointHistResource extends Resource<PointHist> {
	public PointHistResource(PointHist pointHist, Link... links) {
		super(pointHist, links);
		add(linkTo(PointHistController.class).slash(pointHist.getId()).withSelfRel());

	}
}
