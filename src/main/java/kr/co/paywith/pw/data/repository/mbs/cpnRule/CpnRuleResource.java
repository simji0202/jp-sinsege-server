package kr.co.paywith.pw.data.repository.mbs.cpnRule;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CpnRuleResource extends Resource<CpnRule> {

    public CpnRuleResource(CpnRule cpnRule, Link... links) {
        super(cpnRule, links);
        add(linkTo(CpnRuleController.class).slash(cpnRule.getId()).withSelfRel());

    }
}
