package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PrpayIssuResource extends Resource<PrpayIssu> {
	public PrpayIssuResource(PrpayIssu prpayIssu, Link... links) {
		super(prpayIssu, links);
		add(linkTo(PrpayIssuController.class).slash(prpayIssu.getId()).withSelfRel());

	}
}
