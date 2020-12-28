package kr.co.paywith.pw.data.repository.od.ordrComment;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrdrCommentResource extends Resource<OrdrComment> {
	public OrdrCommentResource(OrdrComment ordrComment, Link... links) {
		super(ordrComment, links);
		add(linkTo(OrdrCommentController.class).slash(ordrComment.getId()).withSelfRel());

	}
}
