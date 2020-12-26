package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ScoreHistResource extends Resource<ScoreHist> {
	public ScoreHistResource(ScoreHist scoreHist, Link... links) {
		super(scoreHist, links);
		add(linkTo(ScoreHistController.class).slash(scoreHist.getId()).withSelfRel());

	}
}
