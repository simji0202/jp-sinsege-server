package kr.co.paywith.pw.data.repository.mbs.billingChrg;

import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BillingChrgResource extends Resource<BillingChrg> {

    public BillingChrgResource(BillingChrg billingChrg, Link... links) {
        super(billingChrg, links);
        add(linkTo(BillingChrgController.class).slash(billingChrg.getId()).withSelfRel());

    }
}
