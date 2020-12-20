package kr.co.paywith.pw.data.repository.mbs.billing;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BillingResource extends Resource<Billing> {

    public BillingResource(Billing billing, Link... links) {
        super(billing, links);
        add(linkTo(BillingController.class).slash(billing.getId()).withSelfRel());

    }
}
