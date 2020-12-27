package kr.co.paywith.pw.data.repository.mbs.point;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PointResource extends Resource<Point> {
	public PointResource(Point point, Link... links) {
		super(point, links);
		add(linkTo(PointController.class).slash(point.getId()).withSelfRel());

	}
}
