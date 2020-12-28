package kr.co.paywith.pw.data.repository.od.ordrCommentImg;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrCommentImgResource extends Resource<OrdrCommentImg> {
	public OrdrCommentImgResource(OrdrCommentImg ordrCommentImg, Link... links) {
		super(ordrCommentImg, links);
		add(linkTo(OrdrCommentImgController.class).slash(ordrCommentImg.getId()).withSelfRel());

	}
}
