package kr.co.paywith.pw.data.repository.mbs.delngReview;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class DelngReviewResource extends Resource<DelngReview> {
	public DelngReviewResource(DelngReview delngReview, Link... links) {
		super(delngReview, links);
		add(linkTo(DelngReviewController.class).slash(delngReview.getId()).withSelfRel());

	}
}
