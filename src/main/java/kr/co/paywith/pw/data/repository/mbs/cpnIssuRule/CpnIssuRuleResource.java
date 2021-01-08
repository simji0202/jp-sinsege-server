package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CpnIssuRuleResource extends Resource<CpnIssuRule> {

    public CpnIssuRuleResource(CpnIssuRule cpnIssuRule, Link... links) {
        super(cpnIssuRule, links);
        add(linkTo(CpnIssuRuleController.class).slash(cpnIssuRule.getId()).withSelfRel());

    }
}
