package kr.co.paywith.pw.data.repository.mbs.msgRule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class MsgRuleResource extends Resource<MsgRule> {
	public MsgRuleResource(MsgRule msgRule, Link... links) {
		super(msgRule, links);
		add(linkTo(MsgRuleController.class).slash(msgRule.getId()).withSelfRel());

	}
}
