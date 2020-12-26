package kr.co.paywith.pw.data.repository.mbs.prpay;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PrpayResource extends Resource<Prpay> {
	public PrpayResource(Prpay prpay, Link... links) {
		super(prpay, links);
		add(linkTo(PrpayController.class).slash(prpay.getId()).withSelfRel());

	}
}
