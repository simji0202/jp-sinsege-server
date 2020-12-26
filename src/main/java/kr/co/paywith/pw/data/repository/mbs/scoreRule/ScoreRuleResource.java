package kr.co.paywith.pw.data.repository.mbs.scoreRule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ScoreRuleResource extends Resource<ScoreRule> {
	public ScoreRuleResource(ScoreRule scoreRule, Link... links) {
		super(scoreRule, links);
		add(linkTo(ScoreRuleController.class).slash(scoreRule.getId()).withSelfRel());

	}
}
