package kr.co.paywith.pw.data.repository.mbs.pointRule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PointRuleResource extends Resource<PointRule> {
	public PointRuleResource(PointRule pointRule, Link... links) {
		super(pointRule, links);
		add(linkTo(PointRuleController.class).slash(pointRule.getId()).withSelfRel());

	}
}
