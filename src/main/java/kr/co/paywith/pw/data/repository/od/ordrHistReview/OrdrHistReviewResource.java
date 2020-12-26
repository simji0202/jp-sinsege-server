package kr.co.paywith.pw.data.repository.od.ordrHistReview;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrHistReviewResource extends Resource<OrdrHistReview> {
	public OrdrHistReviewResource(OrdrHistReview ordrHistReview, Link... links) {
		super(ordrHistReview, links);
		add(linkTo(OrdrHistReviewController.class).slash(ordrHistReview.getId()).withSelfRel());

	}
}
