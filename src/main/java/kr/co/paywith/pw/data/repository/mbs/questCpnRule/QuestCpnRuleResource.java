package kr.co.paywith.pw.data.repository.mbs.questCpnRule;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class QuestCpnRuleResource extends Resource<QuestCpnRule> {
	public QuestCpnRuleResource(QuestCpnRule questCpnRule, Link... links) {
		super(questCpnRule, links);
		add(linkTo(QuestCpnRuleController.class).slash(questCpnRule.getId()).withSelfRel());

	}
}
