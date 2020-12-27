package kr.co.paywith.pw.data.repository.mbs.pointUseRule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PointUseRuleResource extends Resource<PointUseRule> {
	public PointUseRuleResource(PointUseRule pointUseRule, Link... links) {
		super(pointUseRule, links);
		add(linkTo(PointUseRuleController.class).slash(pointUseRule.getId()).withSelfRel());

	}
}
