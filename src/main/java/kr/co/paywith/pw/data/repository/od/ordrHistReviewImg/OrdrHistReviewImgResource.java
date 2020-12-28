package kr.co.paywith.pw.data.repository.od.ordrHistReviewImg;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrHistReviewImgResource extends Resource<OrdrHistReviewImg> {
	public OrdrHistReviewImgResource(OrdrHistReviewImg ordrHistReviewImg, Link... links) {
		super(ordrHistReviewImg, links);
		add(linkTo(OrdrHistReviewImgController.class).slash(ordrHistReviewImg.getId()).withSelfRel());

	}
}
