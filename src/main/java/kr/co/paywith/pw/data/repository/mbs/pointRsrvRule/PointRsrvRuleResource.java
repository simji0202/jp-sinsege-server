package kr.co.paywith.pw.data.repository.mbs.pointRsrvRule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PointRsrvRuleResource extends Resource<PointRsrvRule> {
	public PointRsrvRuleResource(PointRsrvRule pointRsrvRule, Link... links) {
		super(pointRsrvRule, links);
		add(linkTo(PointRsrvRuleController.class).slash(pointRsrvRule.getId()).withSelfRel());

	}
}
